package com.apiTest.Api.repos.contacts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiTest.Api.domain.contacts.Contact;
import com.apiTest.Api.domain.contacts.Email;


public interface EmailRepository extends JpaRepository<Email, UUID> {

    Email findFirstByContact(Contact contact);

}
