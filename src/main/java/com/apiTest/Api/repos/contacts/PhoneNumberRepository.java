package com.apiTest.Api.repos.contacts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiTest.Api.domain.contacts.Contact;
import com.apiTest.Api.domain.contacts.PhoneNumber;


public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {

    PhoneNumber findFirstByContact(Contact contact);

}
