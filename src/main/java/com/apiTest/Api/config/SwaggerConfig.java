package com.apiTest.Api.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.apiTest.Api.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    

    private ApiInfo apiInfo() {
    return new ApiInfo(
        "Authentication API",
        "API documentation for authentication operations",
        "1.0",
        "Terms of service URL",
        new Contact("Your Name", "www.example.com", "your.email@example.com"),
        "License of API",
        "License URL",
        Collections.emptyList());
}
}
