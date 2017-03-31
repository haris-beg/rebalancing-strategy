package com.harisbeg.rebalancing.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.harisbeg.rebalancing.strategy.intake.InputCsvFileHandler;
import com.harisbeg.rebalancing.strategy.intake.InputFileHandler;
import com.harisbeg.rebalancing.strategy.intake.P123FileHandlerC;
import com.harisbeg.rebalancing.strategy.intake.P123FileHandlerI;
import com.harisbeg.rebalancing.strategy.intake.P123RealizedTxnsHandler;
import com.harisbeg.rebalancing.strategy.intake.P123RealizedTxnsHandlerI;
import com.harisbeg.rebalancing.strategy.persistence.DbHandler;
import com.harisbeg.rebalancing.strategy.persistence.JdbcHandler;
import com.harisbeg.rebalancing.strategy.service.BusinessRulesSvc;
import com.harisbeg.rebalancing.strategy.service.BusinessRulesSvcImpl;
import com.harisbeg.rebalancing.strategy.service.DownloadSvc;
import com.harisbeg.rebalancing.strategy.service.DownloadSvcI;
import com.harisbeg.rebalancing.strategy.service.StatisticalServiceC;
import com.harisbeg.rebalancing.strategy.service.StatisticalServiceI;

@Configuration
public class AppConfig {

	@Bean
	public InputFileHandler inputFileHandler() {
		return new InputCsvFileHandler();
	}

	@Bean
	public DbHandler dbHandler() {
		return new JdbcHandler();
	}

	@Bean
	public StatisticalServiceI statisticalServiceI() {
		return new StatisticalServiceC();
	}
	
	@Bean
	public P123FileHandlerI p123FileHandlerI() {
		return new P123FileHandlerC();
	}
	
	@Bean
	public DownloadSvcI downloadSvc() {
		return new DownloadSvc();
	}
	
	@Bean
	public BusinessRulesSvc businessRulesSvc() {
		return new BusinessRulesSvcImpl();
	}
	
	@Bean
	public P123RealizedTxnsHandlerI p123RealizedTxnsHandler() {
		return new P123RealizedTxnsHandler();
	}
	
}
