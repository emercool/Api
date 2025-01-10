package com.apiTest.Api.model.contacts;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactTagsDTO {

    private Long id;

    @Size(max = 255)
    private String tagId;

}
