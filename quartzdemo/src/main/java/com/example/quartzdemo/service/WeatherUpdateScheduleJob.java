package com.example.quartzdemo.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.example.quartzdemo.constant.WeatherConstants;

@Service
public class WeatherUpdateScheduleJob {
	
	public static final Logger logger=LoggerFactory.getLogger(WeatherUpdateScheduleJob.class);
	
	@Autowired
	Scheduler quartzScheduler;
	
	JobDetail jobDetail;
	CronTrigger cronTrigger;
	
	public void setJobDetail(String jobName, String jobGroup, String cronExpression, Class<? extends QuartzJobBean> jobClass) {
		
		logger.info("Inside setJobDetail : jobName : {}, jobGroup : {},cronExpression : {}, jobClass : {}",jobName,jobGroup,cronExpression,jobClass);
		
		//set jobDataMap
		JobDataMap map=new JobDataMap();
		map.put("latitude", WeatherConstants.latitude);
		map.put("longitude", WeatherConstants.longitude);
		
		//set job detail
		jobDetail=JobBuilder.newJob(jobClass)
				.withIdentity(jobName, jobGroup)
				.setJobData(map)
				.storeDurably(false)
				.build();
		
		//set trigger
		cronTrigger=TriggerBuilder.newTrigger()
				.withIdentity(jobName, jobGroup)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		
		//schedule job
		try {
			quartzScheduler.scheduleJob(jobDetail, cronTrigger);
			logger.info("Weather Update Job has been scheduled : {}, {}",jobDetail,cronTrigger);
		}catch(SchedulerException e) {
			logger.error("Unable to schedule Job : {}", e.getStackTrace());
		}
	}

}
