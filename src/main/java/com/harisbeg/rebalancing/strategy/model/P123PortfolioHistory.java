package com.harisbeg.rebalancing.strategy.model;

import java.util.Date;

public class P123PortfolioHistory {
	
	private Date periodStartDate;
	private double periodReturn;
	
	public Date getPeriodStartDate() {
		return periodStartDate;
	}
	public void setPeriodStartDate(Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}
	public double getPeriodReturn() {
		return periodReturn;
	}
	public void setPeriodReturn(double periodReturn) {
		this.periodReturn = periodReturn;
	}
	
	public String toString() {
		String strValue = "periodStartDate=" + this.getPeriodStartDate().toString() + "; ";
		strValue += " periodReturn=" + String.valueOf(this.getPeriodReturn());
		return strValue;
	}

}
