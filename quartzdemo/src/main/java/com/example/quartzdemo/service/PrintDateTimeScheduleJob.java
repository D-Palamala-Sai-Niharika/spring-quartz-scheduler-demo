package com.example.quartzdemo.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Service
public class PrintDateTimeScheduleJob {
	
	private static final Logger logger=LoggerFactory.getLogger(PrintDateTimeScheduleJob.class);
	
	@Autowired
	Scheduler quartzScheduler;
	
	JobDetail jobDetail;
	CronTrigger trigger;

	public void setJobDetail(String jobName, String jobGroup, String cronExpression, Class<? extends QuartzJobBean> jobClass) {
		
		//create job detail
		jobDetail=JobBuilder.newJob(jobClass)
				.withIdentity(jobName, jobGroup)
				.storeDurably(false)
				.build();
		
		//create cron trigger
		trigger=TriggerBuilder.newTrigger()
				.withIdentity(jobName, jobGroup)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		
		try {
			quartzScheduler.scheduleJob(jobDetail, trigger);
			logger.info("Job Scheduled Successfully");
		}catch(SchedulerException e) {
			logger.error("Failed to Schedule Job");
		}
		
	}

}
