package com.apiTest.Api.service.contacts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.Contact;
import com.apiTest.Api.domain.contacts.Email;
import com.apiTest.Api.model.contacts.EmailDTO;
import com.apiTest.Api.repos.contacts.ContactRepository;
import com.apiTest.Api.repos.contacts.EmailRepository;
import com.apiTest.Api.util.contacts.NotFoundException;


@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final ContactRepository contactRepository;

    public EmailService(final EmailRepository emailRepository,
            final ContactRepository contactRepository) {
        this.emailRepository = emailRepository;
        this.contactRepository = contactRepository;
    }

    public List<EmailDTO> findAll() {
        final List<Email> emails = emailRepository.findAll(Sort.by("id"));
        return emails.stream()
                .map(email -> mapToDTO(email, new EmailDTO()))
                .toList();
    }

    public EmailDTO get(final UUID id) {
        return emailRepository.findById(id)
                .map(email -> mapToDTO(email, new EmailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final EmailDTO emailDTO) {
        final Email email = new Email();
        mapToEntity(emailDTO, email);
        return emailRepository.save(email).getId();
    }

    public void update(final UUID id, final EmailDTO emailDTO) {
        final Email email = emailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(emailDTO, email);
        emailRepository.save(email);
    }

    public void delete(final UUID id) {
        emailRepository.deleteById(id);
    }

    private EmailDTO mapToDTO(final Email email, final EmailDTO emailDTO) {
        emailDTO.setId(email.getId());
        emailDTO.setEmail(email.getEmail());
        emailDTO.setLabel(email.getLabel());
        emailDTO.setContact(email.getContact() == null ? null : email.getContact().getId());
        return emailDTO;
    }

    private Email mapToEntity(final EmailDTO emailDTO, final Email email) {
        email.setEmail(emailDTO.getEmail());
        email.setLabel(emailDTO.getLabel());
        final Contact contact = emailDTO.getContact() == null ? null : contactRepository.findById(emailDTO.getContact())
                .orElseThrow(() -> new NotFoundException("contact not found"));
        email.setContact(contact);
        return email;
    }

}
