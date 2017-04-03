package com.application.springbootstart.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("classpath:File.properties")
public class ApplicationProperties {

	private String SharedLocation;

	public String getSharedLocation() {
		return SharedLocation;
	}

	public void setSharedLocation(String sharedLocation) {
		SharedLocation = sharedLocation;
	}	    
}
