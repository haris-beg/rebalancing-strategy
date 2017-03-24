package com.harisbeg.rebalancing.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Determines the results of a rebalancing strategy between 2 or more  assets.
 * 
 * @author r625361
 *
 */
@SpringBootApplication
public class RebalancingStrategyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RebalancingStrategyApplication.class, args);
	}
	
	@Autowired
	DbValidator dbValidator;

	@Autowired
	InputFileHandler inputFileHandler;
	
	 @Override
	 public void run(String... strings) throws Exception {
		 inputFileHandler.process("GLD", ".csv");
		 dbValidator.count();
	 }
}
