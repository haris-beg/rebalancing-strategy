package com.harisbeg.rebalancing.strategy;

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

public class InputCsvFileHandler implements InputFileHandler {
	
	private static final Log log = LogFactory.getLog(InputCsvFileHandler.class);
	private static final String FILEPATH = "C:\\git-repos\\download-stock-histories\\csv1\\";
	
	@Override
	public void process(String ticker, String extension) {
		log.info("Inside InputCsvFileHandler.process()...");
		String filename = FILEPATH + ticker + extension;
		int recordCount = 0;
		//Read one CSV file and load it into in-memory database
		try {
			log.info("Reading file " + filename);
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				YahooHistory yahooHistory = parseYahooHistoryRecord(record);
				if (++recordCount < 3) {
					yahooHistory.print();
				}
				
//			    String columnOne = record.get(0);
//			    String columnTwo = record.get(1);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Number of records in input file = " + recordCount);
	}

	private YahooHistory parseYahooHistoryRecord(CSVRecord record) {
		YahooHistory yahooHistory = new YahooHistory();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			yahooHistory.setPriceDate(df.parse(record.get("Date")));
			yahooHistory.setOpeningPrice(Float.parseFloat(record.get("Open")));
			yahooHistory.setHighPrice(Float.parseFloat(record.get("High")));
			yahooHistory.setLowPrice(Float.parseFloat(record.get("Low")));
			yahooHistory.setClosingPrice(Float.parseFloat(record.get("Close")));
			yahooHistory.setAdjClose(Float.parseFloat(record.get("Adj Close")));
			yahooHistory.setPeriodVolume(Long.parseLong(record.get("Volume")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yahooHistory;
	}

}
