package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagControllerException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@ComponentScan("com.epam.esm")

@RequestMapping(value = "/tags")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping()
    @ResponseBody
    public List<Tag> getAllTags() {
        return tagsService.getAllTags();

    }

    @GetMapping("/{id}")
    @ResponseBody
    public Tag getTagById(@PathVariable("id") Long id) throws TagControllerException {
        try {
            return tagsService.getTagById(id);
        } catch (TagNotFoundException e) {
            throw new TagControllerException(e);
        }

    }


    @PostMapping()
    public void create(@RequestBody Tag tag) throws TagControllerException {
        try {
            tagsService.createTag(tag);
        } catch (TagServiceException e) {
            throw new TagControllerException(e);
        }

    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) throws TagControllerException {
        try {
            tagsService.deleteTag(id);
        } catch (TagServiceException e) {
            throw new TagControllerException(e);
        }

    }
}
