package com.example.quartzdemo.service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import com.example.quartzdemo.job.EmailJob;
import com.example.quartzdemo.payload.ScheduleEmailRequest;

@Service
public class EmailScheduleJob {
	
	//build Job Details
	public JobDetail buildJobDetail(ScheduleEmailRequest emailRequest) {
		
		//set JobDataMap
		JobDataMap jobDataMap=new JobDataMap();
		jobDataMap.put("email", emailRequest.getEmail());
		jobDataMap.put("subject", emailRequest.getSubject());
		jobDataMap.put("body", emailRequest.getBody());
		
		//set Job Details
		JobDetail jobDetail= JobBuilder.newJob(EmailJob.class)
				.withIdentity(UUID.randomUUID().toString(), "email-job")
				.setJobData(jobDataMap)
				.withDescription("Send email job")
				.storeDurably()
				.build();
		
		return jobDetail;
	}
	
	//build Trigger
	public Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startsAt) {
		
		Trigger trigger=TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), "email-trigger")
				.withDescription("Send  email trigger")
				.startAt(Date.from(startsAt.toInstant()))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
				.build();
		
		return trigger;
	}
	
}
