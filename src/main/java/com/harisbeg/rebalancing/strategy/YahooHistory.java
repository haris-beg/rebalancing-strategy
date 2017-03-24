package com.harisbeg.rebalancing.strategy;

import java.util.Date;

public class YahooHistory {
	
	private Date priceDate;
	private float openingPrice;
	private float highPrice;
	private float lowPrice;
	private float closingPrice;
	private float adjClose;
	private long daysVolume;
	
	public YahooHistory(Date priceDate, float openingPrice, float highPrice, float lowPrice, float closingPrice,
			float adjClose, long daysVolume) {
		super();
		this.priceDate = priceDate;
		this.openingPrice = openingPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closingPrice = closingPrice;
		this.adjClose = adjClose;
		this.daysVolume = daysVolume;
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
	public long getDaysVolume() {
		return daysVolume;
	}
	public void setDaysVolume(long daysVolume) {
		this.daysVolume = daysVolume;
	}
	
}
