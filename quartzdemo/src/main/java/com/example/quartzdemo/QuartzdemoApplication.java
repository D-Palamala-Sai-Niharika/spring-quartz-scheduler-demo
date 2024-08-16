package com.example.quartzdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.quartzdemo.service.PrintDateTimeJobInitializer;
import com.example.quartzdemo.service.WeatherUpdateJobInitializer;

@SpringBootApplication
public class QuartzdemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext=SpringApplication.run(QuartzdemoApplication.class, args);
		
		// Initialize PrintDateTime Job
		PrintDateTimeJobInitializer printDateTimeInitializer=applicationContext.getBean(PrintDateTimeJobInitializer.class);
		printDateTimeInitializer.printDateTimeInitializerSetJobDetail();

		// Initialize WeatherUpdate Job
		WeatherUpdateJobInitializer weatherUpdateJobInitializer=applicationContext.getBean(WeatherUpdateJobInitializer.class);
		weatherUpdateJobInitializer.weatherUpdateJobInitializerSetJobDetail();
	}

}
