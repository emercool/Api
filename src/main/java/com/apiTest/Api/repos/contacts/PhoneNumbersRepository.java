package com.apiTest.Api.repos.contacts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiTest.Api.domain.contacts.PhoneNumbers;


public interface PhoneNumbersRepository extends JpaRepository<PhoneNumbers, UUID> {
}
