package com.apiTest.Api.model.contacts;


import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailsDTO {

    private UUID id;

    private UUID contactId;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String label;

}
