package com.contact.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contact.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Long> {

	public Optional<Contact> getContactByCode(String code);
}
