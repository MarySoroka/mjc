package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ControllerEntityDeleteException;
import com.epam.esm.exception.ControllerSaveEntityException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.exception.TagServiceException;
import com.epam.esm.resource.TagResource;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/tags")
public class TagsController {

  private final TagService tagService;

  @Autowired
  public TagsController(TagService tagService) {
    this.tagService = tagService;

  }


  @GetMapping
  public ResponseEntity<CollectionModel<TagResource>> getAllTags(
      @RequestParam Long limit,
      @RequestParam Long offset) {
    final List<TagResource> tagResources =
        tagService.getLimitTags(limit, offset).stream().map(TagResource::new)
            .collect(Collectors.toList());
    final CollectionModel<TagResource> resources = CollectionModel.of(tagResources);
    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    resources.add(Link.of(uriString, "self"));
    return ResponseEntity.ok(resources);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TagResource> getTagById(@PathVariable("id") Long id)
      throws EntityNotFoundException {
    Tag tagById = tagService.getTagById(id);
    TagResource tagResource = new TagResource(tagById);
    return ResponseEntity.ok(tagResource);
  }

  @GetMapping("/widelyUsedTag")
  public ResponseEntity<TagResource> getMostWidelyUsedTag() {
    TagResource tagResource = new TagResource(tagService.getTheMostWidelyUsedTag());
    return ResponseEntity.ok(tagResource);
  }

  @PostMapping
  public ResponseEntity<TagResource> create(@RequestBody Tag tag)
      throws ControllerSaveEntityException {
    try {

      Tag createdTag = tagService.createTag(tag);
      TagResource tagResource = new TagResource(createdTag);
      return ResponseEntity.ok(tagResource);
    } catch (TagServiceException | RepositorySaveException | EntityNotFoundException e) {
      throw new ControllerSaveEntityException("Controller exception : Couldn't create tag", e);
    }

  }


  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id)
      throws ControllerEntityDeleteException {
    try {
      tagService.deleteTag(id);
      return ResponseEntity.noContent().build();
    } catch (TagServiceException | RepositoryDeleteException e) {
      throw new ControllerEntityDeleteException("Controller exception : Couldn't delete tag", e);
    }

  }

}
