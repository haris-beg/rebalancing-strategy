package com.harisbeg.rebalancing.strategy;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculate the following stats for a given ticker:
 * - the history start date
 * - the history end date
 * - 
 * RULES:
 * - New money is always invested on the 
 * @author r625361
 *
 */
public class StatisticalServiceC implements StatisticalServiceI {

	private static final Logger log = LoggerFactory.getLogger(StatisticalServiceC.class);

	@Autowired
	DbHandler dbHandler;

	@Override
	public void calculateStatsFor(String ticker) {
		Date historyStartDate = dbHandler.getHistoryStartDateFor(ticker);
		log.info("historyStartDate for ticker " + ticker + " is " + historyStartDate.toString());
		
		
	}

}
