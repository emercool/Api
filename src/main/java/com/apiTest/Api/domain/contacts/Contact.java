package com.apiTest.Api.domain.contacts;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Contact {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String avatar;

    @Column
    private String background;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String company;

    @Column
    private OffsetDateTime birthday;

    @Column(columnDefinition = "text")
    private String address;

    @Column(columnDefinition = "text")
    private String notes;

    @OneToMany(mappedBy = "contact")
    private Set<Email> contactEmails;

    @OneToMany(mappedBy = "contact")
    private Set<PhoneNumber> contactPhoneNumbers;

    @ManyToMany
    @JoinTable(
            name = "ContactTag",
            joinColumns = @JoinColumn(name = "contactId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private Set<Tag> contactTagTags;

}
