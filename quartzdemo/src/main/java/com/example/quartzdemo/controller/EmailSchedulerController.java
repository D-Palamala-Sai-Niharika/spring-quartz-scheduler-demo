package com.example.quartzdemo.controller;

import java.time.ZonedDateTime;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quartzdemo.payload.ScheduleEmailRequest;
import com.example.quartzdemo.payload.ScheduleEmailResponse;
import com.example.quartzdemo.service.EmailScheduleJob;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EmailSchedulerController {
	
	@Autowired
	Scheduler quartzScheduler;
	
	@Autowired
	EmailScheduleJob emailScheduleJob;

	@GetMapping("/test")
	public String testEmailApi() {
		return "Get Api - Test Passed";
	}
	
	@PostMapping("/scheduleEmail")
	public ResponseEntity<ScheduleEmailResponse> sendEmailAtScheduledTime(@Valid @RequestBody ScheduleEmailRequest emailRequest){
		
		try {
			ZonedDateTime dateTime=ZonedDateTime.of(emailRequest.getDateTime(), emailRequest.getTimeZone());
			
			//check if its before current date time
			if(dateTime.isBefore(ZonedDateTime.now())) {
				ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false, "Invalid dateTime. Must be after current dateTime !!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(scheduleEmailResponse);
			}
			
			//schedule job
			JobDetail jobDetail = this.emailScheduleJob.buildJobDetail(emailRequest);
			Trigger trigger = this.emailScheduleJob.buildTrigger(jobDetail, dateTime);
			quartzScheduler.scheduleJob(jobDetail, trigger);
			
			log.info("Job has been successfully scheduled: jobDetail : {}, trigger : {}",jobDetail, trigger);
			
			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true, "Email Scheduled Successfully !!",jobDetail.getKey().getName(),jobDetail.getKey().getGroup());
			
			return new ResponseEntity<ScheduleEmailResponse>(scheduleEmailResponse, HttpStatus.CREATED);
			
		}catch(SchedulerException ex) {
			log.error("Failed to schedule job", ex.getStackTrace());
			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false, "Failed to Schedule Email. Please try again later !!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
		}
		
	}
}
