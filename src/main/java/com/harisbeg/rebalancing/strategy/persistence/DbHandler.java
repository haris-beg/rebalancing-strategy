package com.harisbeg.rebalancing.strategy.persistence;

import java.util.Date;

import com.harisbeg.rebalancing.strategy.model.P123PortfolioHistory;
import com.harisbeg.rebalancing.strategy.model.P123RealizedTxn;
import com.harisbeg.rebalancing.strategy.model.YahooHistory;

public interface DbHandler {

	int yahooHistoryCount();

	int yahooHistoryCount(String ticker);

	void loadYahooHistoryRecord(YahooHistory yahooHistory);

	Date getHistoryStartDateFor(String ticker);

	void loadP123HistoryRecord(P123PortfolioHistory p123History);

	int p123HistoryCount();

	void loadP123RealizedTxn(P123RealizedTxn p123RealizedTxn);

	int p123RealizedTxnCount();

}
