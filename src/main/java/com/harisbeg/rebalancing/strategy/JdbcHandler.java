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

}
