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

import com.apiTest.Api.model.contacts.PhoneNumberDTO;
import com.apiTest.Api.service.contacts.PhoneNumberService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/phoneNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhoneNumberResource {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberResource(final PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping
    public ResponseEntity<List<PhoneNumberDTO>> getAllPhoneNumbers() {
        return ResponseEntity.ok(phoneNumberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumberDTO> getPhoneNumber(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(phoneNumberService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createPhoneNumber(
            @RequestBody @Valid final PhoneNumberDTO phoneNumberDTO) {
        final UUID createdId = phoneNumberService.create(phoneNumberDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updatePhoneNumber(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final PhoneNumberDTO phoneNumberDTO) {
        phoneNumberService.update(id, phoneNumberDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable(name = "id") final UUID id) {
        phoneNumberService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
