package com.apiTest.Api.rest.contacts;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiTest.Api.model.contacts.ContactTagsDTO;
import com.apiTest.Api.service.contacts.ContactTagsService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/contactTagss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactTagsResource {

    private final ContactTagsService contactTagsService;

    public ContactTagsResource(final ContactTagsService contactTagsService) {
        this.contactTagsService = contactTagsService;
    }

    @GetMapping
    public ResponseEntity<List<ContactTagsDTO>> getAllContactTagss() {
        return ResponseEntity.ok(contactTagsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactTagsDTO> getContactTags(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(contactTagsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createContactTags(
            @RequestBody @Valid final ContactTagsDTO contactTagsDTO) {
        final Long createdId = contactTagsService.create(contactTagsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateContactTags(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ContactTagsDTO contactTagsDTO) {
        contactTagsService.update(id, contactTagsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteContactTags(@PathVariable(name = "id") final Long id) {
        contactTagsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
