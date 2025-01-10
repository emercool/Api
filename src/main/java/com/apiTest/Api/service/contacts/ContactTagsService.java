package com.apiTest.Api.service.contacts;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.ContactTags;
import com.apiTest.Api.model.contacts.ContactTagsDTO;
import com.apiTest.Api.repos.contacts.ContactTagsRepository;
import com.apiTest.Api.util.contacts.NotFoundException;


@Service
public class ContactTagsService {

    private final ContactTagsRepository contactTagsRepository;

    public ContactTagsService(final ContactTagsRepository contactTagsRepository) {
        this.contactTagsRepository = contactTagsRepository;
    }

    public List<ContactTagsDTO> findAll() {
        final List<ContactTags> contactTagses = contactTagsRepository.findAll(Sort.by("id"));
        return contactTagses.stream()
                .map(contactTags -> mapToDTO(contactTags, new ContactTagsDTO()))
                .toList();
    }

    public ContactTagsDTO get(final Long id) {
        return contactTagsRepository.findById(id)
                .map(contactTags -> mapToDTO(contactTags, new ContactTagsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ContactTagsDTO contactTagsDTO) {
        final ContactTags contactTags = new ContactTags();
        mapToEntity(contactTagsDTO, contactTags);
        return contactTagsRepository.save(contactTags).getId();
    }

    public void update(final Long id, final ContactTagsDTO contactTagsDTO) {
        final ContactTags contactTags = contactTagsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contactTagsDTO, contactTags);
        contactTagsRepository.save(contactTags);
    }

    public void delete(final Long id) {
        contactTagsRepository.deleteById(id);
    }

    private ContactTagsDTO mapToDTO(final ContactTags contactTags,
            final ContactTagsDTO contactTagsDTO) {
        contactTagsDTO.setId(contactTags.getId());
        contactTagsDTO.setTagId(contactTags.getTagId());
        return contactTagsDTO;
    }

    private ContactTags mapToEntity(final ContactTagsDTO contactTagsDTO,
            final ContactTags contactTags) {
        contactTags.setTagId(contactTagsDTO.getTagId());
        return contactTags;
    }

}
