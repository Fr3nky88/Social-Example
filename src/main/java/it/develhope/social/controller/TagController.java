package it.develhope.social.controller;

import it.develhope.social.exceptions.PostException;
import it.develhope.social.model.TagDto;
import it.develhope.social.model.TagEto;
import it.develhope.social.model.TagInput;
import it.develhope.social.services.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tag")
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService tagService;
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagEto> getAll() {
        return tagService.getAll();
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insert(@RequestBody TagInput tag) {
        try {
            return ResponseEntity.ok().body(tagService.insert(tag));
        } catch (PostException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
