package com.example.quartzdemo.payload;

import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleEmailRequest {
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String subject;
	
	@NotEmpty
	private String body;
	
	@NotNull
	private LocalDateTime dateTime;
	
	@NotNull
	private ZoneId timeZone;
	
}
