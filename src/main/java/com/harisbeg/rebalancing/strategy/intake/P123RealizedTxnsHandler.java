package com.harisbeg.rebalancing.strategy.intake;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.harisbeg.rebalancing.strategy.AppConstants;
import com.harisbeg.rebalancing.strategy.model.P123PortfolioHistory;
import com.harisbeg.rebalancing.strategy.model.P123RealizedTxn;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;
import com.harisbeg.rebalancing.strategy.service.BusinessRulesSvc;
import com.harisbeg.rebalancing.strategy.service.DownloadSvcI;

public class P123RealizedTxnsHandler implements P123RealizedTxnsHandlerI {
	
	@Value("${max.sell3.txns}")
	private int maxSell3Txns;
	
	@Value("${skip.all.realized.txns.except.one.out.of.every}")
	private int skipSllRealizedTxnsExceptOneOutOfEvery;
	
	@Autowired
	DbHandler dbHandler;

	@Autowired
	DownloadSvcI downloadSvc;

	@Autowired
	BusinessRulesSvc businessRulesSvc;

	private static final Log log = LogFactory.getLog(P123RealizedTxnsHandler.class);

	@Override
	public void process(String inputFileName) {
		log.info("Inside P123RealizedTxnsHandler.process()...");
		String filename = AppConstants.p123RealizedTxnFilePath + inputFileName + AppConstants.p123InputFileExtension;
		int recordCount = 0;
		int sell3TxnCount = 0;
		int totalDaysHeldOld = 0;
		int totalDaysHeldNew = 0;
		float avgDaysHeldOld = 0;
		float avgDaysHeldNew = 0;
		double totalSlippage = 0.0;
		double averageSlippage = 0.0;
		try {
			log.info("Reading file " + filename);
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				P123RealizedTxn p123RealizedTxn = parseP123RealizedTxn(record);
				recordCount++;
				if (recordCount % skipSllRealizedTxnsExceptOneOutOfEvery != 0) {
					//skip all except 1 out of every skipSllRealizedTxnsExceptOneOutOfEvery to speed up execution
					continue;
				}
				log.debug(p123RealizedTxn.toString());
				if (p123RealizedTxn.getNote().contains("Sell3") && !p123RealizedTxn.getTicker().contains("^")) {
					dbHandler.loadP123RealizedTxn(p123RealizedTxn);
					String ticker = p123RealizedTxn.getTicker();
					Date oldBuyDate = p123RealizedTxn.getPositionOpenDate();
					downloadSvc.download(ticker);
					float oldBuyPrice = dbHandler.getOpenPrice(ticker, oldBuyDate);
					log.debug("oldBuyPrice for " + ticker + " = " + oldBuyPrice);
					if (oldBuyPrice == -1) {
						log.debug("Skipping " + ticker + " because of bad data");
						continue;
					}
					sell3TxnCount++;
					if (sell3TxnCount > maxSell3Txns) {
						break;
					}
					int daysHeldOld = p123RealizedTxn.getDaysHeld();
					totalDaysHeldOld += daysHeldOld;
					Date newBuyDate = getNewBuyDate(oldBuyDate);
					int daysHeldNew = daysHeldOld - (int) ((newBuyDate.getTime() - oldBuyDate.getTime())/86400000);
					log.debug("daysHeldNew for " + ticker + " = " + daysHeldNew);
					totalDaysHeldNew += daysHeldNew;
					float newBuyPrice = getNewBuyPrice(ticker, newBuyDate);
					log.debug("newBuyPrice for " + ticker + " = " + newBuyPrice);
					double slippage = (oldBuyPrice - newBuyPrice)/oldBuyPrice;
					log.debug("slippage for " + ticker + " = " + slippage);
					totalSlippage += slippage;
					if (sell3TxnCount % 50 == 0) {
						averageSlippage = totalSlippage/sell3TxnCount;
						log.info("Average Slippage for the first " + sell3TxnCount + " transactions = " + averageSlippage*100 + "%");
					}
				}
			}
			if (sell3TxnCount > 0) {
				log.info("totalSlippage = " + totalSlippage);
				averageSlippage = totalSlippage/sell3TxnCount;
				log.info("averageSlippage = (-ve is bad, +ve is good) " + averageSlippage*100 + "%");
				avgDaysHeldOld = totalDaysHeldOld/sell3TxnCount;
				log.info("avgDaysHeldOld = " + avgDaysHeldOld);
				avgDaysHeldNew = totalDaysHeldNew/sell3TxnCount;
				log.info("avgDaysHeldNew = " + avgDaysHeldNew);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Number of records in input file = " + recordCount);
	}

	/**
	 * Use Business Rules to determine the new Buy price
	 * Examples are to buy at:
	 * 	opening price of the day
	 * 	closing price of the day
	 * 	high price of the day, etc
	 * @param ticker
	 * @param newBuyDate
	 * @return
	 */
	private float getNewBuyPrice(String ticker, Date newBuyDate) {
		return businessRulesSvc.getNewBuyPrice(ticker, newBuyDate);
	}

	/**
	 * Use the Business Rules to determine what should be the new Buy date for the ticker
	 * Examples would be:
	 * 	buy on the oldBuyDate, OR
	 * 	buy on the next trading day after the oldBuyDate, OR
	 * 	buy on the next to next trading day after the oldBuyDate, OR
	 * 	buy on the first trading day of the next week after the oldBuyDate, etc
	 * @param oldBuyDate
	 * @return
	 */
	private Date getNewBuyDate(Date oldBuyDate) {
		return businessRulesSvc.getNewBuyDate(oldBuyDate);
	}

	private P123RealizedTxn parseP123RealizedTxn(CSVRecord record) {
		P123RealizedTxn p123RealizedTxn = new P123RealizedTxn();
		DateFormat df = new SimpleDateFormat(AppConstants.p123PeriodStartDateFormat);
		p123RealizedTxn.setTicker(record.get("Symbol"));
		p123RealizedTxn.setDaysHeld(Integer.parseInt(record.get("Days")));
		p123RealizedTxn.setNote(record.get("Note"));
		try {
			p123RealizedTxn.setPositionOpenDate(df.parse(record.get("Open")));
			p123RealizedTxn.setPositionCloseDate(df.parse(record.get("Close")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return p123RealizedTxn;
	}

}
