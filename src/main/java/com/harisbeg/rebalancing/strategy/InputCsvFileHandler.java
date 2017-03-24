package com.harisbeg.rebalancing.strategy;

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
import org.springframework.beans.factory.annotation.Value;

public class InputCsvFileHandler implements InputFileHandler {
	
	@Value("${input.file.path}")
	private String inputFilePath;
	
	@Value("${input.file.extension}")
	private String inputFileExtension;
	
	@Value("${price.date.format}")
	private String priceDateFormat;

	@Autowired
	DbHandler dbHandler;
	
	private static final Log log = LogFactory.getLog(InputCsvFileHandler.class);
	
	@Override
	public void process(String ticker) {
		log.info("Inside InputCsvFileHandler.process()...");
		String filename = inputFilePath + ticker + inputFileExtension;
		int recordCount = 0;
		try {
			log.info("Reading file " + filename);
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				YahooHistory yahooHistory = parseYahooHistoryRecord(record);
				recordCount++;
//				if (recordCount < 2) {
//					yahooHistory.print();
//				}
				dbHandler.loadYahooHistoryRecord(yahooHistory);
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
			DateFormat df = new SimpleDateFormat(priceDateFormat);
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
