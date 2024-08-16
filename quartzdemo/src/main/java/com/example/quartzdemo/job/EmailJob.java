package com.example.quartzdemo.job;

import java.nio.charset.StandardCharsets;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailJob extends QuartzJobBean {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailProperties mailProperties;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("Executing Job with key {}", context.getJobDetail().getKey());
		//retrieve JobDataMap
		JobDataMap jobDataMap=context.getMergedJobDataMap();
		String recipientEmail=jobDataMap.getString("email");
		String subject=jobDataMap.getString("subject");
		String body=jobDataMap.getString("body");
		log.info("recipientEmail : {} , subject : {}, body : {}", recipientEmail, subject, body);
		
		//send email using java mail sender
		sendEmail(mailProperties.getUsername(),recipientEmail,subject,body);
	}

	private void sendEmail(String fromEmail, String toEmail, String subject, String body) {
		try {
			//Email messages with Multipurpose Internet Mail Extension (MIME) formatting are transmitted with SMTP
			MimeMessage message= mailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);
			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(toEmail);
			
			mailSender.send(message);
			log.info("Message sent successfully to {}",toEmail);
			
		}catch(MessagingException ex) {
			log.error("Failed to send the message.Try again :{}",ex.getStackTrace());
		}		
		
	}

}
