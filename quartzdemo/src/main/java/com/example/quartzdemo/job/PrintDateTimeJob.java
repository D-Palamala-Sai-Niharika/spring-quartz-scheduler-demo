package com.example.quartzdemo.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class PrintDateTimeJob extends QuartzJobBean {
	
	private static final Logger logger=LoggerFactory.getLogger(PrintDateTimeJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			String currentDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			logger.info("Current Date and Time : {}", currentDateTime);	
		}catch(Exception e) {
			logger.error("Unable to fetch Date and Time", e.getStackTrace());
		}
	}
}
