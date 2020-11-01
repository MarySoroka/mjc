package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagControllerException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.service.TagsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tags")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagsService.getAllTags();

    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") Long id) throws TagControllerException {
        try {
            return tagsService.getTagById(id);
        } catch (TagNotFoundException e) {
            throw new TagControllerException(e);
        }

    }


    @PostMapping
    public Long create(@RequestBody Tag tag) throws TagControllerException {
        try {
            return tagsService.createTag(tag);
        } catch (TagServiceException e) {
            throw new TagControllerException(e);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws TagControllerException {
        try {
            tagsService.deleteTag(id);
            return new ResponseEntity<>("Delete tag entity successfully. Id :" + id,
                HttpStatus.OK);
        } catch (TagServiceException e) {
            throw new TagControllerException(e);
        }

    }

}
