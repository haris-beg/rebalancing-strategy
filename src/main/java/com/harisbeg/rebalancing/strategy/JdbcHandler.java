package com.harisbeg.rebalancing.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcHandler implements DbHandler {
	
	private static final Logger log = LoggerFactory.getLogger(JdbcHandler.class);
	
	@Value("${insert.sql}")
	private String insertSql;

	@Value("${get.history.start.date.sql}")
	private String getHistoryStartDateSql;

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
		jdbcTemplate.update(insertSql, yh.getTicker(), yh.getPriceDate(), yh.getOpeningPrice(), yh.getHighPrice(), 
				yh.getLowPrice(), yh.getClosingPrice(), yh.getAdjClose(), yh.getPeriodVolume());
	}

	@Override
	public Date getHistoryStartDateFor(String ticker) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
