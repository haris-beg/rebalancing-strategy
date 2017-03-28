package com.harisbeg.rebalancing.strategy.intake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.harisbeg.rebalancing.strategy.AppConstants;
import com.harisbeg.rebalancing.strategy.model.P123PortfolioHistory;
import com.harisbeg.rebalancing.strategy.model.YahooHistory;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;

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

public class P123FileHandlerC implements P123FileHandlerI {

	@Autowired
	DbHandler dbHandler;

	private static final Log log = LogFactory.getLog(P123FileHandlerC.class);

	@Override
	public void process(String inputFileName) {
		log.info("Inside P123FileHandlerC.process()...");
		String filename = AppConstants.p123InputFilePath + inputFileName + AppConstants.p123InputFileExtension;
		int recordCount = 0;
		try {
			log.info("Reading file " + filename);
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				P123PortfolioHistory p123History = parseP123HistoryRecord(record);
				recordCount++;
				log.debug(p123History.toString());
				dbHandler.loadP123HistoryRecord(p123History);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Number of records in input file = " + recordCount);
	}

	private P123PortfolioHistory parseP123HistoryRecord(CSVRecord record) {
		P123PortfolioHistory p123History = new P123PortfolioHistory();
		DateFormat df = new SimpleDateFormat(AppConstants.p123PeriodStartDateFormat);
		try {
			p123History.setPeriodStartDate(df.parse(record.get("periodStartDate")));
			p123History.setPeriodReturn(Double.parseDouble(record.get("periodReturn")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p123History;
	}

}
