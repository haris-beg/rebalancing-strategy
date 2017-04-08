package com.harisbeg.rebalancing.strategy.model;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahooHistory {
	
	private static final Logger log = LoggerFactory.getLogger(YahooHistory.class);
	
	private String ticker;
	private Date priceDate;
	private float openingPrice;
	private float highPrice;
	private float lowPrice;
	private float closingPrice;
	private float adjClose;
	private long periodVolume;
	private int tradingDayNum;
	
	public YahooHistory(String ticker, Date priceDate, float openingPrice, float highPrice, float lowPrice, float closingPrice,
			float adjClose, long periodVolume, int tradingDayNum) {
		super();
		this.ticker = ticker;
		this.priceDate = priceDate;
		this.openingPrice = openingPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closingPrice = closingPrice;
		this.adjClose = adjClose;
		this.periodVolume = periodVolume;
		this.tradingDayNum = tradingDayNum;
	}

	public YahooHistory() {
		// TODO Auto-generated constructor stub
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Date getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	public float getOpeningPrice() {
		return openingPrice;
	}
	public void setOpeningPrice(float openingPrice) {
		this.openingPrice = openingPrice;
	}
	public float getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(float highPrice) {
		this.highPrice = highPrice;
	}
	public float getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(float lowPrice) {
		this.lowPrice = lowPrice;
	}
	public float getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(float closingPrice) {
		this.closingPrice = closingPrice;
	}
	public float getAdjClose() {
		return adjClose;
	}
	public void setAdjClose(float adjClose) {
		this.adjClose = adjClose;
	}
	public long getPeriodVolume() {
		return periodVolume;
	}
	public void setPeriodVolume(long daysVolume) {
		this.periodVolume = daysVolume;
	}

	public int getTradingDayNum() {
		return tradingDayNum;
	}

	public void setTradingDayNum(int tradingDayNum) {
		this.tradingDayNum = tradingDayNum;
	}

	public void print() {
		log.info("priceDate=" + this.getPriceDate().toString());
		log.info("openingPrice=" + String.valueOf(this.getOpeningPrice()));
		log.info("highPrice=" + String.valueOf(this.getHighPrice()));
		log.info("lowPrice=" + String.valueOf(this.getLowPrice()));
		log.info("closingPrice=" + String.valueOf(this.getClosingPrice()));
		log.info("periodVolume=" + String.valueOf(this.getPeriodVolume()));
		log.info("adjClose=" + String.valueOf(this.getAdjClose()));
		log.info("tradingDayNum=" + String.valueOf(this.getTradingDayNum()));
	}
	
}
