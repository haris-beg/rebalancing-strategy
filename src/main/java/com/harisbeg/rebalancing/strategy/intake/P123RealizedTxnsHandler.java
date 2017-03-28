package com.harisbeg.rebalancing.strategy.intake;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.harisbeg.rebalancing.strategy.AppConstants;
import com.harisbeg.rebalancing.strategy.model.P123PortfolioHistory;
import com.harisbeg.rebalancing.strategy.model.P123RealizedTxn;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;

public class P123RealizedTxnsHandler implements P123RealizedTxnsHandlerI {

	@Autowired
	DbHandler dbHandler;

	private static final Log log = LogFactory.getLog(P123RealizedTxnsHandler.class);

	@Override
	public void process(String inputFileName) {
		log.info("Inside P123RealizedTxnsHandler.process()...");
		String filename = AppConstants.p123RealizedTxnFilePath + inputFileName + AppConstants.p123InputFileExtension;
		int recordCount = 0;
		int sell3Count = 0;
		try {
			log.info("Reading file " + filename);
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				P123RealizedTxn p123RealizedTxn = parseP123RealizedTxn(record);
				recordCount++;
				log.debug(p123RealizedTxn.toString());
				if (p123RealizedTxn.getNote().contains("Sell3") && !p123RealizedTxn.getTicker().contains("^")) {
					dbHandler.loadP123RealizedTxn(p123RealizedTxn);
					sell3Count++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Number of records in input file = " + recordCount);
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
