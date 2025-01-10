package com.apiTest.Api.model.contacts;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactsDTO {

    private UUID id;

    @Size(max = 255)
    private String avatar;

    @Size(max = 255)
    private String background;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String company;

    private LocalDateTime birthday;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String notes;

}
