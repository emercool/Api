package com.apiTest.Api.model.contacts;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactDTO {

    private UUID id;

    @Size(max = 255)
    private String avatar;

    @Size(max = 255)
    private String background;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String title;

    @Size(max = 100)
    private String company;

    private OffsetDateTime birthday;

    private String address;

    private String notes;

    private List<UUID> contactTagTags;

}
