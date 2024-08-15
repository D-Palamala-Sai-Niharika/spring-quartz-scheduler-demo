package com.example.quartzdemo.service;

import com.example.quartzdemo.job.PrintDateTimeJob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrintDateTimeInitializer {
	
	@Autowired
	PrintDateTimeScheduleJob printDateTimeScheduleJob;
	
	@Value("${printDateTimeJob.name}")
	String jobName;
	
	@Value("${printDateTimeJob.group}")
	String jobGroup;
	
	@Value("${printDateTimeJob.cron.timing}")
	String cronExpression;
	
	public void printDateTimeInitializerSetJobDetail() {
		this.printDateTimeScheduleJob.setJobDetail(jobName, jobGroup, cronExpression, PrintDateTimeJob.class);
	}
}
