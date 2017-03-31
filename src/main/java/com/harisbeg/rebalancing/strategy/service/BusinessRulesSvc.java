package com.harisbeg.rebalancing.strategy.service;

import java.util.Date;

public interface BusinessRulesSvc {

	Date getNewBuyDate(Date oldBuyDate);

	float getNewBuyPrice(String ticker, Date newBuyDate);

}
