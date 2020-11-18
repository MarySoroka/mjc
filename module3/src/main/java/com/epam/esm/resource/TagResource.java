package com.epam.esm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.epam.esm.controller.TagsController;
import com.epam.esm.entity.Tag;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.RepresentationModel;

public class TagResource extends RepresentationModel<TagResource> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TagResource.class);
  private final Tag tag;

  public TagResource(Tag tag) {
    this.tag = tag;
    LOGGER.info("Current tag:{} ", tag);
    add(linkTo(TagsController.class).withRel("tags"));

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    TagResource that = (TagResource) o;
    return Objects.equals(tag, that.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), tag);
  }

  public Tag getTag() {
    return tag;
  }
}
