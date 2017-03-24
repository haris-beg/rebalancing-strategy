package com.harisbeg.rebalancing.strategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
			for (CSVRecord record : records) {
				recordCount++;
//			    String columnOne = record.get(0);
//			    String columnTwo = record.get(1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Number of records in input file = " + recordCount);
	}

}
