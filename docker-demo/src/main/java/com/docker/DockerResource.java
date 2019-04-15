package com.docker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docker")
public class DockerResource {

	@GetMapping("/demo")
	public String dockerDemo()
	{
		return "Docker Hello !!";
	}
}
