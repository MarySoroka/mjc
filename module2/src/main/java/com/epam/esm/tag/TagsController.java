package com.epam.esm.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping()
    public String getAllTags() {
        tagsService.getAllTags();
        return null;
    }

    @GetMapping("/{id}")
    public String getTagById(@PathVariable("id") Long id) {
        try {
            tagsService.getTagById(id);
        } catch (TagNotFoundException e) {
            e.printStackTrace();
        }
        return "tags/";
    }


    @PostMapping()
    public String create(@RequestBody Tag tag) {
        tagsService.createTag(tag);
        return "redirect:/people";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            tagsService.deleteTag(id);
        } catch (TagDeleteException e) {
            e.printStackTrace();
        }
        return "redirect:/people";
    }
}
