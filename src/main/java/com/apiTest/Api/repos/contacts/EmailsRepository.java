package com.apiTest.Api.repos.contacts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiTest.Api.domain.contacts.Emails;


public interface EmailsRepository extends JpaRepository<Emails, UUID> {
}
