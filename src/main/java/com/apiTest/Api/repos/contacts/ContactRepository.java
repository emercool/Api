package com.apiTest.Api.repos.contacts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiTest.Api.domain.contacts.Contact;
import com.apiTest.Api.domain.contacts.Tag;


public interface ContactRepository extends JpaRepository<Contact, UUID> {

    Contact findFirstByContactTagTags(Tag tag);

    List<Contact> findAllByContactTagTags(Tag tag);

}
