package com.apiTest.Api.repos.contacts;

import com.apiTest.Api.domain.contacts.ContactTags;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactTagsRepository extends JpaRepository<ContactTags, Long> {
}
