package com.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@SpringBootApplication
@ComponentScan({"com.zuul","com.security"})
public class ZuulNetflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulNetflixApplication.class, args);
	}

}
