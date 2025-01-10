package com.apiTest.Api.service.contacts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.Contact;
import com.apiTest.Api.domain.contacts.PhoneNumber;
import com.apiTest.Api.model.contacts.PhoneNumberDTO;
import com.apiTest.Api.repos.contacts.ContactRepository;
import com.apiTest.Api.repos.contacts.PhoneNumberRepository;
import com.apiTest.Api.util.contacts.NotFoundException;


@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final ContactRepository contactRepository;

    public PhoneNumberService(final PhoneNumberRepository phoneNumberRepository,
            final ContactRepository contactRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.contactRepository = contactRepository;
    }

    public List<PhoneNumberDTO> findAll() {
        final List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll(Sort.by("id"));
        return phoneNumbers.stream()
                .map(phoneNumber -> mapToDTO(phoneNumber, new PhoneNumberDTO()))
                .toList();
    }

    public PhoneNumberDTO get(final UUID id) {
        return phoneNumberRepository.findById(id)
                .map(phoneNumber -> mapToDTO(phoneNumber, new PhoneNumberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final PhoneNumberDTO phoneNumberDTO) {
        final PhoneNumber phoneNumber = new PhoneNumber();
        mapToEntity(phoneNumberDTO, phoneNumber);
        return phoneNumberRepository.save(phoneNumber).getId();
    }

    public void update(final UUID id, final PhoneNumberDTO phoneNumberDTO) {
        final PhoneNumber phoneNumber = phoneNumberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(phoneNumberDTO, phoneNumber);
        phoneNumberRepository.save(phoneNumber);
    }

    public void delete(final UUID id) {
        phoneNumberRepository.deleteById(id);
    }

    private PhoneNumberDTO mapToDTO(final PhoneNumber phoneNumber,
            final PhoneNumberDTO phoneNumberDTO) {
        phoneNumberDTO.setId(phoneNumber.getId());
        phoneNumberDTO.setCountry(phoneNumber.getCountry());
        phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
        phoneNumberDTO.setLabel(phoneNumber.getLabel());
        phoneNumberDTO.setContact(phoneNumber.getContact() == null ? null : phoneNumber.getContact().getId());
        return phoneNumberDTO;
    }

    private PhoneNumber mapToEntity(final PhoneNumberDTO phoneNumberDTO,
            final PhoneNumber phoneNumber) {
        phoneNumber.setCountry(phoneNumberDTO.getCountry());
        phoneNumber.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
        phoneNumber.setLabel(phoneNumberDTO.getLabel());
        final Contact contact = phoneNumberDTO.getContact() == null ? null : contactRepository.findById(phoneNumberDTO.getContact())
                .orElseThrow(() -> new NotFoundException("contact not found"));
        phoneNumber.setContact(contact);
        return phoneNumber;
    }

}
