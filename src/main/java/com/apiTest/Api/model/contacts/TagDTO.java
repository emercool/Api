package com.apiTest.Api.model.contacts;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

}
