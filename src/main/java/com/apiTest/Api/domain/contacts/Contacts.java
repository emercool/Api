package com.apiTest.Api.domain.contacts;


import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Contacts {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String avatar;

    @Column
    private String background;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private String company;

    @Column
    private LocalDateTime birthday;

    @Column
    private String address;

    @Column
    private String notes;

}
