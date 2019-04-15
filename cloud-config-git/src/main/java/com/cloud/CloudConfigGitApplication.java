package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class CloudConfigGitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudConfigGitApplication.class, args);
	}

}

