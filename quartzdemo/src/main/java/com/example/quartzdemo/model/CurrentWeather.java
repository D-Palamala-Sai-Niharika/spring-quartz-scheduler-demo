package com.example.quartzdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class CurrentWeather {
	private double temperature;
	@JsonProperty("windspeed")
	private double windSpeed;
	@JsonProperty("is_day")
	private Integer isDay;
}
