package com.harisbeg.rebalancing.strategy;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcHandlerTest {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	DbHandler dbHandler;
	
	String ticker = "AAPL";
	GregorianCalendar calendar = new GregorianCalendar(2016, 1, 15);
	Date priceDate = new Date(calendar.getTimeInMillis());
	float openingPrice = (float) 123.67;
	
	@Before
	public void setUp() {
		jdbcTemplate.update("DELETE FROM yahooHistory WHERE ticker = ?", ticker);
		jdbcTemplate.update("INSERT INTO yahooHistory (ticker, priceDate, openingPrice) VALUES (?, ?, ?)", ticker, priceDate, openingPrice);
	}
	
	@Test
	public void getOpenPriceTest() {
		float openPrice = dbHandler.getOpenPrice(ticker, priceDate);
		float delta = 0.01f;
		assertEquals(openingPrice, openPrice, delta);
	}
	
	@Test
	public void yahooHistoryCountTest() {
		int yahooHistoryCount = dbHandler.yahooHistoryCount();
		assertEquals(1, yahooHistoryCount);
	}
	
	@Test
	public void yahooHistoryCountTickerTest() {
		int yahooHistoryCount = dbHandler.yahooHistoryCount(ticker);
		assertEquals(1, yahooHistoryCount);
	}
	
	@Test
	public void p123HistoryCountTest() {
		int p123HistoryCount = dbHandler.p123HistoryCount();
		assertEquals(0, p123HistoryCount);
	}
	
	@Test
	public void p123RealizedTxnCountTest() {
		int p123RealizedTxnCount = dbHandler.p123RealizedTxnCount();
		assertEquals(0, p123RealizedTxnCount);
	}
		
	@After
	public void tearDown() {
		jdbcTemplate.update("DELETE FROM yahooHistory WHERE ticker = ?", ticker);
	}

}
