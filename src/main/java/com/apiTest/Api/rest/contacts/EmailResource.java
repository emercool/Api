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

import com.apiTest.Api.model.contacts.EmailDTO;
import com.apiTest.Api.service.contacts.EmailService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/emails", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailResource {

    private final EmailService emailService;

    public EmailResource(final EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<EmailDTO>> getAllEmails() {
        return ResponseEntity.ok(emailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailDTO> getEmail(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(emailService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createEmail(@RequestBody @Valid final EmailDTO emailDTO) {
        final UUID createdId = emailService.create(emailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateEmail(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final EmailDTO emailDTO) {
        emailService.update(id, emailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEmail(@PathVariable(name = "id") final UUID id) {
        emailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
