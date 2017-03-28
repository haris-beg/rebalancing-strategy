package com.harisbeg.rebalancing.strategy.model;

import java.util.Date;

public class P123RealizedTxn {
	
	private String ticker;
	private Date positionOpenDate;
	private Date positionCloseDate;
	private int daysHeld;
	private String note;
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public Date getPositionOpenDate() {
		return positionOpenDate;
	}
	public void setPositionOpenDate(Date positionOpenDate) {
		this.positionOpenDate = positionOpenDate;
	}
	public Date getPositionCloseDate() {
		return positionCloseDate;
	}
	public void setPositionCloseDate(Date positionCloseDate) {
		this.positionCloseDate = positionCloseDate;
	}
	public int getDaysHeld() {
		return daysHeld;
	}
	public void setDaysHeld(int daysHeld) {
		this.daysHeld = daysHeld;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String toString() {
		String strValue = "Ticker = " + this.getTicker();
		strValue += "; positionOpenDate = " + this.getPositionOpenDate();
		strValue += "; positionCloseDate = " + this.getPositionCloseDate();
		strValue += "; daysHeld = " + this.getDaysHeld();
		strValue += "; note = " + this.getNote();
		return strValue;
	}
}
