package com.apiTest.Api.rest.contacts;

import java.util.List;
import java.util.UUID;

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

import com.apiTest.Api.model.contacts.ContactsDTO;
import com.apiTest.Api.service.contacts.ContactsService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/contactss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactsResource {

    private final ContactsService contactsService;

    public ContactsResource(final ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GetMapping
    public ResponseEntity<List<ContactsDTO>> getAllContactss() {
        return ResponseEntity.ok(contactsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactsDTO> getContacts(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(contactsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createContacts(@RequestBody @Valid final ContactsDTO contactsDTO) {
        final UUID createdId = contactsService.create(contactsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateContacts(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final ContactsDTO contactsDTO) {
        contactsService.update(id, contactsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteContacts(@PathVariable(name = "id") final UUID id) {
        contactsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
