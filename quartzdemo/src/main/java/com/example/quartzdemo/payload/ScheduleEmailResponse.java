package com.example.quartzdemo.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) //ignore null fields when serializing an object to JSON
public class ScheduleEmailResponse {
	private boolean success;
	private String message;
	private String jobId;
	private String jobGroup;
	
	public ScheduleEmailResponse(boolean success,String message){
		super();
		this.success=success;
		this.message=message;
	}

	public ScheduleEmailResponse(boolean success, String message, String jobId, String jobGroup) {
		super();
		this.success = success;
		this.message = message;
		this.jobId = jobId;
		this.jobGroup = jobGroup;
	}
	
	
}
