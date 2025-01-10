package com.apiTest.Api.service.contacts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.Contacts;
import com.apiTest.Api.model.contacts.ContactsDTO;
import com.apiTest.Api.repos.contacts.ContactsRepository;
import com.apiTest.Api.util.contacts.NotFoundException;


@Service
public class ContactsService {

    private final ContactsRepository contactsRepository;

    public ContactsService(final ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    public List<ContactsDTO> findAll() {
        final List<Contacts> contactses = contactsRepository.findAll(Sort.by("id"));
        return contactses.stream()
                .map(contacts -> mapToDTO(contacts, new ContactsDTO()))
                .toList();
    }

    public ContactsDTO get(final UUID id) {
        return contactsRepository.findById(id)
                .map(contacts -> mapToDTO(contacts, new ContactsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ContactsDTO contactsDTO) {
        final Contacts contacts = new Contacts();
        mapToEntity(contactsDTO, contacts);
        return contactsRepository.save(contacts).getId();
    }

    public void update(final UUID id, final ContactsDTO contactsDTO) {
        final Contacts contacts = contactsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contactsDTO, contacts);
        contactsRepository.save(contacts);
    }

    public void delete(final UUID id) {
        contactsRepository.deleteById(id);
    }

    private ContactsDTO mapToDTO(final Contacts contacts, final ContactsDTO contactsDTO) {
        contactsDTO.setId(contacts.getId());
        contactsDTO.setAvatar(contacts.getAvatar());
        contactsDTO.setBackground(contacts.getBackground());
        contactsDTO.setName(contacts.getName());
        contactsDTO.setTitle(contacts.getTitle());
        contactsDTO.setCompany(contacts.getCompany());
        contactsDTO.setBirthday(contacts.getBirthday());
        contactsDTO.setAddress(contacts.getAddress());
        contactsDTO.setNotes(contacts.getNotes());
        return contactsDTO;
    }

    private Contacts mapToEntity(final ContactsDTO contactsDTO, final Contacts contacts) {
        contacts.setAvatar(contactsDTO.getAvatar());
        contacts.setBackground(contactsDTO.getBackground());
        contacts.setName(contactsDTO.getName());
        contacts.setTitle(contactsDTO.getTitle());
        contacts.setCompany(contactsDTO.getCompany());
        contacts.setBirthday(contactsDTO.getBirthday());
        contacts.setAddress(contactsDTO.getAddress());
        contacts.setNotes(contactsDTO.getNotes());
        return contacts;
    }

}
