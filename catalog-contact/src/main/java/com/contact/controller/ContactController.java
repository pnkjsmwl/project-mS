package com.contact.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact.entity.Contact;
import com.contact.service.ContactService;

@RestController
@RequestMapping("/contact")
public class ContactController {

	private final ContactService service;

	@Autowired
	public ContactController(ContactService service) {
		this.service=service;
	}

	@GetMapping("/{code}")
	public Optional<Contact> getContactByCode(@PathVariable String code)
	{
		return service.findContactByCode(code);
	}
}
