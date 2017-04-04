package com.harisbeg.rebalancing.strategy.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.harisbeg.rebalancing.strategy.persistence.DbHandler;
import com.harisbeg.rebalancing.strategy.utils.DateUtils;
public class BusinessRulesSvcImpl implements BusinessRulesSvc {

	@Value("${buying.time.of.day}")
	private String buyingTimeOfDay;

	@Value("${buy.date.offset}")
	private int buyDateOffset;

	@Autowired
	DbHandler dbHandler;

	@Override
	public Date getNewBuyDate(Date oldBuyDate) {
		return DateUtils.getNewDate(oldBuyDate, buyDateOffset);
	}

	@Override
	public float getNewBuyPrice(String ticker, Date newBuyDate) {
		float newBuyPrice = -1;
		if (buyingTimeOfDay.equalsIgnoreCase("close")) {
			newBuyPrice = dbHandler.getClosingPrice(ticker, newBuyDate);
		} else if (buyingTimeOfDay.equalsIgnoreCase("open")) {
			newBuyPrice = dbHandler.getOpenPrice(ticker, newBuyDate);
		}
		return newBuyPrice;
	}

}
