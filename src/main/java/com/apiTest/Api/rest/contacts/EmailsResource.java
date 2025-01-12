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

import com.apiTest.Api.model.contacts.EmailsDTO;
import com.apiTest.Api.service.contacts.EmailsService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/emailss", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailsResource {

    private final EmailsService emailsService;

    public EmailsResource(final EmailsService emailsService) {
        this.emailsService = emailsService;
    }

    @GetMapping
    public ResponseEntity<List<EmailsDTO>> getAllEmailss() {
        return ResponseEntity.ok(emailsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailsDTO> getEmails(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(emailsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createEmails(@RequestBody @Valid final EmailsDTO emailsDTO) {
        final UUID createdId = emailsService.create(emailsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateEmails(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final EmailsDTO emailsDTO) {
        emailsService.update(id, emailsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEmails(@PathVariable(name = "id") final UUID id) {
        emailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
