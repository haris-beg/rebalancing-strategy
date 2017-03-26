package com.harisbeg.rebalancing.strategy;

import java.util.Date;

public interface DbHandler {

	void count();

	void loadYahooHistoryRecord(YahooHistory yahooHistory);

	Date getHistoryStartDateFor(String ticker);

}
