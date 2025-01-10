package com.apiTest.Api.service.contacts;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.Contact;
import com.apiTest.Api.domain.contacts.Email;
import com.apiTest.Api.domain.contacts.PhoneNumber;
import com.apiTest.Api.domain.contacts.Tag;
import com.apiTest.Api.model.contacts.ContactDTO;
import com.apiTest.Api.repos.contacts.ContactRepository;
import com.apiTest.Api.repos.contacts.EmailRepository;
import com.apiTest.Api.repos.contacts.PhoneNumberRepository;
import com.apiTest.Api.repos.contacts.TagRepository;
import com.apiTest.Api.util.contacts.NotFoundException;
import com.apiTest.Api.util.contacts.ReferencedWarning;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final TagRepository tagRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public ContactService(final ContactRepository contactRepository,
            final TagRepository tagRepository, final EmailRepository emailRepository,
            final PhoneNumberRepository phoneNumberRepository) {
        this.contactRepository = contactRepository;
        this.tagRepository = tagRepository;
        this.emailRepository = emailRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public List<ContactDTO> findAll() {
        final List<Contact> contacts = contactRepository.findAll(Sort.by("id"));
        return contacts.stream()
                .map(contact -> mapToDTO(contact, new ContactDTO()))
                .toList();
    }

    public ContactDTO get(final UUID id) {
        return contactRepository.findById(id)
                .map(contact -> mapToDTO(contact, new ContactDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ContactDTO contactDTO) {
        final Contact contact = new Contact();
        mapToEntity(contactDTO, contact);
        return contactRepository.save(contact).getId();
    }

    public void update(final UUID id, final ContactDTO contactDTO) {
        final Contact contact = contactRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contactDTO, contact);
        contactRepository.save(contact);
    }

    public void delete(final UUID id) {
        contactRepository.deleteById(id);
    }

    private ContactDTO mapToDTO(final Contact contact, final ContactDTO contactDTO) {
        contactDTO.setId(contact.getId());
        contactDTO.setAvatar(contact.getAvatar());
        contactDTO.setBackground(contact.getBackground());
        contactDTO.setName(contact.getName());
        contactDTO.setTitle(contact.getTitle());
        contactDTO.setCompany(contact.getCompany());
        contactDTO.setBirthday(contact.getBirthday());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setNotes(contact.getNotes());
        contactDTO.setContactTagTags(contact.getContactTagTags().stream()
                .map(tag -> tag.getId())
                .toList());
        return contactDTO;
    }

    private Contact mapToEntity(final ContactDTO contactDTO, final Contact contact) {
        contact.setAvatar(contactDTO.getAvatar());
        contact.setBackground(contactDTO.getBackground());
        contact.setName(contactDTO.getName());
        contact.setTitle(contactDTO.getTitle());
        contact.setCompany(contactDTO.getCompany());
        contact.setBirthday(contactDTO.getBirthday());
        contact.setAddress(contactDTO.getAddress());
        contact.setNotes(contactDTO.getNotes());
        final List<Tag> contactTagTags = tagRepository.findAllById(
                contactDTO.getContactTagTags() == null ? Collections.emptyList() : contactDTO.getContactTagTags());
        if (contactTagTags.size() != (contactDTO.getContactTagTags() == null ? 0 : contactDTO.getContactTagTags().size())) {
            throw new NotFoundException("one of contactTagTags not found");
        }
        contact.setContactTagTags(new HashSet<>(contactTagTags));
        return contact;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Contact contact = contactRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Email contactEmail = emailRepository.findFirstByContact(contact);
        if (contactEmail != null) {
            referencedWarning.setKey("contact.email.contact.referenced");
            referencedWarning.addParam(contactEmail.getId());
            return referencedWarning;
        }
        final PhoneNumber contactPhoneNumber = phoneNumberRepository.findFirstByContact(contact);
        if (contactPhoneNumber != null) {
            referencedWarning.setKey("contact.phoneNumber.contact.referenced");
            referencedWarning.addParam(contactPhoneNumber.getId());
            return referencedWarning;
        }
        return null;
    }

}
