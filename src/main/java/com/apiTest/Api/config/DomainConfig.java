package com.apiTest.Api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.apiTest.Api.domain.contacts")
@EnableJpaRepositories("com.apiTest.Api.repos.contacts")
@EnableTransactionManagement
public class DomainConfig {
}
