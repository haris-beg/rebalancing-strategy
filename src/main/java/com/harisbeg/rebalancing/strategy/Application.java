package com.harisbeg.rebalancing.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.harisbeg.rebalancing.strategy.intake.InputFileHandler;
import com.harisbeg.rebalancing.strategy.intake.P123FileHandlerI;
import com.harisbeg.rebalancing.strategy.intake.P123RealizedTxnsHandlerI;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;
import com.harisbeg.rebalancing.strategy.persistence.JdbcHandler;
import com.harisbeg.rebalancing.strategy.service.BacktestServiceI;
import com.harisbeg.rebalancing.strategy.service.DownloadSvcI;
import com.harisbeg.rebalancing.strategy.service.StatisticalServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Determines the results of a rebalancing strategy between 2 or more  assets.
 * 
 * @author r625361
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	DbHandler dbHandler;

	@Autowired
	InputFileHandler inputFileHandler;
	
	@Autowired
	StatisticalServiceI statisticalServiceI;

//	@Autowired
//	BacktestServiceI backtestService;

	@Autowired
	P123FileHandlerI p123FileHandler;

	@Autowired
	P123RealizedTxnsHandlerI p123RealizedTxnsHandler;

	@Autowired
	DownloadSvcI downloadSvc;

	@Override
	 public void run(String... strings) throws Exception {
		p123RealizedTxnsHandler.process("P123_PortPL_MyTradIRAsimulated");
		log.info("No of recs loaded into the p123RealizedTxns table = " + dbHandler.p123RealizedTxnCount());
//		p123FileHandler.process("p123WeeklyHistory");
//		log.info("No of recs loaded into the p123History table = " + dbHandler.p123HistoryCount());
		downloadSvc.download("GLD");
		log.info("No of recs loaded into the yahooHistory table = " + dbHandler.yahooHistoryCount());
		
//		 inputFileHandler.process("GLD");
//		 dbHandler.yahooHistoryCount();
//		 inputFileHandler.process("SPY");
//		 dbHandler.yahooHistoryCount();
//		 statisticalServiceI.calculateStatsFor("GLD");
//		 statisticalServiceI.calculateStatsFor("SPY");
//		 backtestService.backtest("SPY", "GLD");
	 }
}
