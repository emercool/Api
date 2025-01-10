package com.apiTest.Api.model.contacts;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PhoneNumberDTO {

    private UUID id;

    @NotNull
    @Size(max = 2)
    private String country;

    @NotNull
    @Size(max = 50)
    private String phoneNumber;

    @NotNull
    @Size(max = 50)
    private String label;

    private UUID contact;

}
