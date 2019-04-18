package com.contact.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.contact.entity.Contact;
import com.contact.repo.ContactRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactService {

	private final ContactRepo repo;
	private final Environment env;

	@Autowired
	public ContactService(ContactRepo repo, Environment env) {
		this.repo=repo;
		this.env=env;
	}

	public Optional<Contact> findContactByCode(String code)
	{
		log.info("In Contact Service, response from port : "+env.getProperty("local.server.port"));
		return repo.getContactByCode(code);
	}
}
