package com.example.quartzdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Weather {
	private double latitude;
	private double longitude;
	@JsonProperty("current_weather")
	private CurrentWeather currentWeather;
}
