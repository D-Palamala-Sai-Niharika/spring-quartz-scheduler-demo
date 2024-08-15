package com.example.quartzdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.quartzdemo.service.PrintDateTimeInitializer;

@SpringBootApplication
public class QuartzdemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext=SpringApplication.run(QuartzdemoApplication.class, args);
		
		// Initialize PrintDateTime Job
		PrintDateTimeInitializer printDateTimeInitializer=applicationContext.getBean(PrintDateTimeInitializer.class);
		printDateTimeInitializer.printDateTimeInitializerSetJobDetail();
	}

}
