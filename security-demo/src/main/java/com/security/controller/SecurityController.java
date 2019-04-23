package com.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/display")
public class SecurityController {

	@GetMapping("/unsecured")
	public String unsecured()
	{
		return "Unsecured !!";
	}

	@PreAuthorize("hasAnyRole('ADMIN','DBA')")
	@GetMapping("/secured")
	public String secured()
	{
		return "Secured !!";
	}
}
