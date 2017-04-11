package com.harisbeg.rebalancing.strategy.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDateUtils {
	
	public static boolean isWeekend(Date someDate) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(someDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if 	(dayOfWeek == 1 || dayOfWeek == 7) {
			return true;
		}
		return false;
	}

}
