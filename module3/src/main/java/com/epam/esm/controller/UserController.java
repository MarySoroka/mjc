package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.resource.UserResource;
import com.epam.esm.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<CollectionModel<UserResource>> getAllUsers(@RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "10") Integer limit) {
    final List<UserResource> userResources = userService.getAllUsers(limit, offset).stream().map(UserResource::new)
        .collect(Collectors.toList());
    final CollectionModel<UserResource> resources = CollectionModel.of(userResources);
    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    resources.add(Link.of(uriString).withSelfRel());
    return ResponseEntity.ok(resources);

  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResource> getUserById(@PathVariable("id") Long id) throws EntityNotFoundException {
    User user = userService.getById(id);
    UserResource userResource = new UserResource(user);
    return ResponseEntity.ok(userResource);
  }
}
