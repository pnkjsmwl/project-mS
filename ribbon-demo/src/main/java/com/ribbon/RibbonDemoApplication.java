package com.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/* This is the ribbon client class */
@RibbonClient(name="ribbon-server")
@SpringBootApplication
public class RibbonDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbonDemoApplication.class, args);
	}

	
	@Bean
	@LoadBalanced
	public RestTemplate restTemp()
	{
		return new RestTemplate();
	}
}
