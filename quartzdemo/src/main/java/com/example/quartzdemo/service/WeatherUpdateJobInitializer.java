package com.example.quartzdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.quartzdemo.job.WeatherUpdateJob;

@Service
public class WeatherUpdateJobInitializer {
	
	@Autowired
	WeatherUpdateScheduleJob weatherUpdateScheduleJob;
	
	@Value("${weatherUpdateJob.name}")
	String jobName;
	
	@Value("${weatherUpdateJob.group}")
	String jobGroup;
	
	@Value("${weatherUpdateJob.cron.timing}")
	String cronExpression;
	
	
	public void weatherUpdateJobInitializerSetJobDetail(){
		weatherUpdateScheduleJob.setJobDetail(jobName,jobGroup,cronExpression,WeatherUpdateJob.class);
	}

}
