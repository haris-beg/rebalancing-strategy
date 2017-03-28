package com.harisbeg.rebalancing.strategy;

public class AppConstants {
	
	public static final String p123HistoryInsertSql = "INSERT INTO p123History (periodStartDate, periodReturn) VALUES (?, ?)"; 
	public static final String p123RealizedTxnInsertSql = "INSERT INTO p123RealizedTxns (ticker, positionOpenDate, positionCloseDate, daysHeld) "
			+ "VALUES (?, ?, ?, ?)"; 
	public static final String p123HistoryCountSql = "SELECT count(*) FROM p123History"; 
	public static final String p123RealizedTxnCountSql = "SELECT count(*) FROM p123RealizedTxns"; 
	public static final String yahooTickerHistoryCountSql = "SELECT count(*) FROM yahooHistory WHERE ticker = ";
	public static final String yahooHistoryCountSql = "SELECT count(*) FROM yahooHistory";
	
	public static final String p123PeriodStartDateFormat = "MM/dd/yyyy";
	public static final String yahooPriceDateFormat = "yyyy-MM-dd";
	
	public static final String p123InputFilePath = "csv/p123/";
	public static final String p123RealizedTxnFilePath = "csv/p123/realized/";
	public static final String yahooHistoryFilePath = "csv/yahoo/";
	
	public static final String p123InputFileExtension = ".csv";
	public static final String yahooHistoryFileExtension = ".csv";
	
	public static final String yahooFinUrlStr = "http://chart.finance.yahoo.com/table.csv?s=";

}
