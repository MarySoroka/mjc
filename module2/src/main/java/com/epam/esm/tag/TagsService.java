package com.epam.esm.tag;

import com.epam.esm.gift_certificate.GiftCertificate;

import java.util.List;

public interface TagsService {
    List<Tag> getAllTags();
    Tag getTagById(Long id) throws TagNotFoundException;
    void createTag(Tag tag);
    void deleteTag(Long tagId) throws TagDeleteException;


}
