package com.harisbeg.rebalancing.strategy.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.harisbeg.rebalancing.strategy.AppConstants;
import com.harisbeg.rebalancing.strategy.model.P123PortfolioHistory;
import com.harisbeg.rebalancing.strategy.model.P123RealizedTxn;
import com.harisbeg.rebalancing.strategy.model.YahooHistory;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcHandler implements DbHandler {
	
	private static final Logger log = LoggerFactory.getLogger(JdbcHandler.class);
	
	@Value("${yahoo.history.insert.sql}")
	private String yahooHistoryInsertSql;
	
	@Value("${get.history.start.date.sql}")
	private String getHistoryStartDateSql;

	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public int yahooHistoryCount() {
		return jdbcTemplate.queryForObject(AppConstants.yahooHistoryCountSql, Integer.class);
	}

	@Override
	public int yahooHistoryCount(String ticker) {
		return jdbcTemplate.queryForObject(AppConstants.yahooTickerHistoryCountSql + "'" + ticker + "'", Integer.class);
	}

	@Override
	public int p123HistoryCount() {
		return jdbcTemplate.queryForObject(AppConstants.p123HistoryCountSql, Integer.class);
	}

	@Override
	public int p123RealizedTxnCount() {
		return jdbcTemplate.queryForObject(AppConstants.p123RealizedTxnCountSql, Integer.class);
	}

	@Override
	public void loadYahooHistoryRecord(YahooHistory yh) {
		jdbcTemplate.update(yahooHistoryInsertSql, yh.getTicker(), yh.getPriceDate(), yh.getOpeningPrice(), yh.getHighPrice(), 
				yh.getLowPrice(), yh.getClosingPrice(), yh.getAdjClose(), yh.getPeriodVolume());
	}

	@Override
	public void loadP123HistoryRecord(P123PortfolioHistory p123History) {
		jdbcTemplate.update(AppConstants.p123HistoryInsertSql, p123History.getPeriodStartDate(), p123History.getPeriodReturn());
	}

	@Override
	public void loadP123RealizedTxn(P123RealizedTxn p123RealizedTxn) {
		jdbcTemplate.update(AppConstants.p123RealizedTxnInsertSql, p123RealizedTxn.getTicker(), p123RealizedTxn.getPositionOpenDate(),
				p123RealizedTxn.getPositionCloseDate(), p123RealizedTxn.getDaysHeld());
	}

	@Override
	public Date getHistoryStartDateFor(String ticker) {
		String historyStartDateSql = getHistoryStartDateSql + "'" + ticker + "'";
		return jdbcTemplate.queryForObject(historyStartDateSql, Date.class);
	}

	@Override
	public float getOpenPrice(String ticker, Date priceDate) {
		float openPrice = -1;
		try {
			openPrice = jdbcTemplate.queryForObject(AppConstants.getOpenPriceForDateSql, Float.class, ticker, priceDate);
		} catch(EmptyResultDataAccessException e) {
			log.error("EmptyResultDataAccessException for " + ticker);
		}
		return openPrice;
	}

	@Override
	public float getClosingPrice(String ticker, Date priceDate) {
		return jdbcTemplate.queryForObject(AppConstants.getClosingPriceForDateSql, Float.class, ticker, priceDate);
	}

}
