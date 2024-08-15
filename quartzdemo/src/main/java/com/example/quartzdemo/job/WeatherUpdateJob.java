package com.example.quartzdemo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.quartzdemo.constant.WeatherConstants;
import com.example.quartzdemo.model.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WeatherUpdateJob extends QuartzJobBean{
	
	public static final Logger logger=LoggerFactory.getLogger(WeatherUpdateJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// Retrieve JobDetail,JobDataMap details from JobExcecutionContext
		String latitude=(String) context.getJobDetail().getJobDataMap().get("latitude");
		String longitude=(String) context.getJobDetail().getJobDataMap().get("longitude");
		
		//set apiUrl
		String apiUrl=WeatherConstants.apiUrl+"?latitude="+latitude+"&longitude="+longitude+"&current_weather=true";
		
		//Job To perform
		try {
			
			//REST Client - Rest Template - HTTP calls
			RestTemplate restTemplate=new RestTemplate();
			String response=restTemplate.getForObject(apiUrl, String.class);
			logger.info("response : {}",response);
			//Weather response=restTemplate.getForObject(apiUrl, Weather.class);
			//logger.info("response : {}",response.getCurrentWeather());
			
			//Object Mapper 
			//writeValueAsString() - stringify - java object to json string
			//readValue(,) - parse - json string to java object
			ObjectMapper objectMapper=new ObjectMapper();
			Weather weatherResponse=objectMapper.readValue(response, Weather.class);
			logger.info("Data Retrived : \n latitude:{} \n longitude:{} \n temperature:{} \n windSpeed:{} \n isDay:{}",
					weatherResponse.getLatitude(),
					weatherResponse.getLongitude(),
					weatherResponse.getCurrentWeather().getTemperature(),
					weatherResponse.getCurrentWeather().getWindSpeed(),
					weatherResponse.getCurrentWeather().getIsDay()==0?"Day":"Night");
		}catch(Exception ex){
			logger.error("Unable to retrieve data: {}", ex.getStackTrace());
		}
		
	}
	

}
