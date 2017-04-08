package com.harisbeg.rebalancing.strategy.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.harisbeg.rebalancing.strategy.AppConstants;
import com.harisbeg.rebalancing.strategy.model.YahooHistory;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;

public class DownloadSvc implements DownloadSvcI {

	private static final Logger log = LoggerFactory.getLogger(DownloadSvc.class);

	@Autowired
	DbHandler dbHandler;

	// Get the stock history for @ticker from Yahoo Finance
	// Save the history in the yahooHistory DB table
	@Override
	public void download(String ticker) {
		// Download the ticker's history only if it hasn't already been downloaded
		// Since the same ticker can appear multiple times in the realized txns list
		if (dbHandler.yahooHistoryCount(ticker) > 0) {
			return;
		}
		log.debug("Downloading Yahoo Finance History for " + ticker);
		String yahooFinUrlStr = AppConstants.yahooFinUrlStr + ticker;
		int rowCounter = 0;
		try {
			URL yahooFinUrl = new URL(yahooFinUrlStr);
			URLConnection data = yahooFinUrl.openConnection();
			Scanner responseScanner = new Scanner(data.getInputStream());

			if (responseScanner.hasNext()) { // first row is header row, so skip it
				log.debug(responseScanner.nextLine());
			}

			while (responseScanner.hasNextLine()) {
				rowCounter++;
				String line = responseScanner.nextLine();
				YahooHistory yahooHistory = parseYahooHistoryRecord(line, ticker, rowCounter);
				dbHandler.loadYahooHistoryRecord(yahooHistory);
//				printYahooScannerLine(line);
			}
			log.debug("Number of rows downloaded for " + ticker + " = " + rowCounter);
			responseScanner.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException happened when downloading Yahoo stock history for " + ticker);;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printYahooScannerLine(String line) {
		Scanner lineScanner = new Scanner(line).useDelimiter(",");
		log.debug(line);
		log.debug("date= " + lineScanner.next());
		log.debug("open= " + lineScanner.nextDouble());
		log.debug("high= " + lineScanner.nextDouble());
		log.debug("low= " + lineScanner.nextDouble());
		log.debug("close= " + lineScanner.nextDouble());
		log.debug("volume= " + lineScanner.nextLong());
		log.debug("adj close= " + lineScanner.nextDouble());
		lineScanner.close();
	}

	private YahooHistory parseYahooHistoryRecord(String line, String ticker, int tradingDayNum) {
		// TODO Auto-generated method stub
		YahooHistory yahooHistory = new YahooHistory();
		yahooHistory.setTicker(ticker);
		Scanner lineScanner = new Scanner(line).useDelimiter(",");
		DateFormat df = new SimpleDateFormat(AppConstants.yahooPriceDateFormat);
		try {
			yahooHistory.setPriceDate(df.parse(lineScanner.next()));
			yahooHistory.setOpeningPrice(lineScanner.nextFloat());
			yahooHistory.setHighPrice(lineScanner.nextFloat());
			yahooHistory.setLowPrice(lineScanner.nextFloat());
			yahooHistory.setClosingPrice(lineScanner.nextFloat());
			yahooHistory.setPeriodVolume(lineScanner.nextLong());
			yahooHistory.setAdjClose(lineScanner.nextFloat());
			yahooHistory.setTradingDayNum(tradingDayNum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lineScanner.close();
		return yahooHistory;
	}

}
