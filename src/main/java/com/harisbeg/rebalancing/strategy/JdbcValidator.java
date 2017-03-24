package com.harisbeg.rebalancing.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcValidator implements DbValidator {
	
	private static final Logger log = LoggerFactory.getLogger(JdbcValidator.class);

	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public void count() {
		String countSql = "SELECT count(*) from yahooHistory";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class);
		log.info("Number of records in yahooHistory table = " + total);
	}

}
