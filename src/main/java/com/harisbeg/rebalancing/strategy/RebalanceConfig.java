package com.harisbeg.rebalancing.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RebalanceConfig {

	@Bean
	public InputFileHandler inputFileHandler() {
		return new InputCsvFileHandler();
	}
}
