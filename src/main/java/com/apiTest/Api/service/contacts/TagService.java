package com.apiTest.Api.service.contacts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apiTest.Api.domain.contacts.Tag;
import com.apiTest.Api.model.contacts.TagDTO;
import com.apiTest.Api.repos.contacts.ContactRepository;
import com.apiTest.Api.repos.contacts.TagRepository;
import com.apiTest.Api.util.contacts.NotFoundException;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final ContactRepository contactRepository;

    public TagService(final TagRepository tagRepository,
            final ContactRepository contactRepository) {
        this.tagRepository = tagRepository;
        this.contactRepository = contactRepository;
    }

    public List<TagDTO> findAll() {
        final List<Tag> tags = tagRepository.findAll(Sort.by("id"));
        return tags.stream()
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .toList();
    }

    public TagDTO get(final UUID id) {
        return tagRepository.findById(id)
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final TagDTO tagDTO) {
        final Tag tag = new Tag();
        mapToEntity(tagDTO, tag);
        return tagRepository.save(tag).getId();
    }

    public void update(final UUID id, final TagDTO tagDTO) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tagDTO, tag);
        tagRepository.save(tag);
    }

    public void delete(final UUID id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        contactRepository.findAllByContactTagTags(tag)
                .forEach(contact -> contact.getContactTagTags().remove(tag));
        tagRepository.delete(tag);
    }

    private TagDTO mapToDTO(final Tag tag, final TagDTO tagDTO) {
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    private Tag mapToEntity(final TagDTO tagDTO, final Tag tag) {
        tag.setName(tagDTO.getName());
        return tag;
    }

}
