package com.apiTest.Api.service.contacts;


import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.Emails;
import com.apiTest.Api.model.contacts.EmailsDTO;
import com.apiTest.Api.repos.contacts.EmailsRepository;
import com.apiTest.Api.util.contacts.NotFoundException;


@Service
public class EmailsService {

    private final EmailsRepository emailsRepository;

    public EmailsService(final EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    public List<EmailsDTO> findAll() {
        final List<Emails> emailses = emailsRepository.findAll(Sort.by("id"));
        return emailses.stream()
                .map(emails -> mapToDTO(emails, new EmailsDTO()))
                .toList();
    }

    public EmailsDTO get(final UUID id) {
        return emailsRepository.findById(id)
                .map(emails -> mapToDTO(emails, new EmailsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final EmailsDTO emailsDTO) {
        final Emails emails = new Emails();
        mapToEntity(emailsDTO, emails);
        return emailsRepository.save(emails).getId();
    }

    public void update(final UUID id, final EmailsDTO emailsDTO) {
        final Emails emails = emailsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(emailsDTO, emails);
        emailsRepository.save(emails);
    }

    public void delete(final UUID id) {
        emailsRepository.deleteById(id);
    }

    private EmailsDTO mapToDTO(final Emails emails, final EmailsDTO emailsDTO) {
        emailsDTO.setId(emails.getId());
        emailsDTO.setContactId(emails.getContactId());
        emailsDTO.setEmail(emails.getEmail());
        emailsDTO.setLabel(emails.getLabel());
        return emailsDTO;
    }

    private Emails mapToEntity(final EmailsDTO emailsDTO, final Emails emails) {
        emails.setContactId(emailsDTO.getContactId());
        emails.setEmail(emailsDTO.getEmail());
        emails.setLabel(emailsDTO.getLabel());
        return emails;
    }

}
