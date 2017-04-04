package com.harisbeg.rebalancing.strategy.utils;

import java.util.Date;

import com.harisbeg.rebalancing.strategy.AppConstants;

public class DateUtils {
	
	public static Date nextCalendarDateAfter(Date oldDate) {
		return new Date(oldDate.getTime() + AppConstants.numOfMilliSecsInADay);
	}
	
	public static Date getNewDate(Date oldDate, int offset) {
		return new Date(oldDate.getTime() + (offset * AppConstants.numOfMilliSecsInADay));
	}

}
