package com.harisbeg.rebalancing.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcHandler implements DbHandler {
	
	private static final Logger log = LoggerFactory.getLogger(JdbcHandler.class);

	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public void count() {
		String countSql = "SELECT count(*) from yahooHistory";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class);
		log.info("Number of records in yahooHistory table = " + total);
	}

	@Override
	public void loadYahooHistoryRecord(YahooHistory yh) {
		final String insertSql = "INSERT INTO yahooHistory (priceDate, openingPrice, highPrice, lowPrice,"
				+ "closingPrice, adjClose, periodVolume) VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, yh.getPriceDate(), yh.getOpeningPrice(), yh.getHighPrice(), 
				yh.getLowPrice(), yh.getClosingPrice(), yh.getAdjClose(), yh.getPeriodVolume());
	}

}
