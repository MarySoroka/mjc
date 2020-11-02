package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerEntityDeleteException;
import com.epam.esm.exception.ControllerEntityNotFoundException;
import com.epam.esm.exception.ControllerSaveEntityException;
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
  public ResponseEntity<List<Tag>> getAllTags() {
    return new ResponseEntity<>(tagsService.getAllTags(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id)
      throws ControllerEntityNotFoundException {
    try {
      Tag tagById = tagsService.getTagById(id);
      return new ResponseEntity<>(tagById, HttpStatus.OK);
    } catch (TagNotFoundException e) {
      throw new ControllerEntityNotFoundException("Controller exception : Couldn't get by id tag",
          e);
    }

  }


  @PostMapping
  public ResponseEntity<Tag> create(@RequestBody Tag tag)
      throws ControllerSaveEntityException, ControllerEntityNotFoundException {
    try {
      Long tagId = tagsService.createTag(tag);
      Tag tagById = tagsService.getTagById(tagId);
      return new ResponseEntity<>(tagById, HttpStatus.CREATED);
    } catch (TagServiceException e) {
      throw new ControllerSaveEntityException("Controller exception : Couldn't create tag", e);
    } catch (TagNotFoundException e) {
      throw new ControllerEntityNotFoundException("Controller exception : Couldn't find tag", e);
    }

  }


  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id)
      throws ControllerEntityDeleteException {
    try {
      tagsService.deleteTag(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (TagServiceException e) {
      throw new ControllerEntityDeleteException("Controller exception : Couldn't delete tag", e);
    }

  }

}
