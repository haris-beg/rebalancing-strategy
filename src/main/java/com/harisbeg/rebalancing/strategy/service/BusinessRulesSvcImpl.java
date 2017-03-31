package com.harisbeg.rebalancing.strategy.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.harisbeg.rebalancing.strategy.persistence.DbHandler;

public class BusinessRulesSvcImpl implements BusinessRulesSvc {

	@Autowired
	DbHandler dbHandler;

	@Override
	public Date getNewBuyDate(Date oldBuyDate) {
		return oldBuyDate;
	}

	@Override
	public float getNewBuyPrice(String ticker, Date newBuyDate) {
		return dbHandler.getClosingPrice(ticker, newBuyDate);
	}

}
