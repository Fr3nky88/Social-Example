package it.develhope.social.controller;

import it.develhope.social.entities.Post;
import it.develhope.social.exceptions.PostException;
import it.develhope.social.exceptions.UserException;
import it.develhope.social.model.MyResponse;
import it.develhope.social.model.PostDto;
import it.develhope.social.model.PostEto;
import it.develhope.social.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(postService.getAllEto());
        } catch (Exception e) {
            logger.error("errore nel get all dei post", e);
            return ResponseEntity.internalServerError().body(new MyResponse("KO","errore nel get all dei post " + e.getMessage() ));
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insert(@RequestBody PostDto post) {
        logger.info("Input method insert: {}", post);
        try {
            postService.insertPost(post);
            return ResponseEntity.ok().body(new MyResponse("OK"));
        } catch (PostException e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new MyResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("errore nell'inserimento del post", e);
            return ResponseEntity.internalServerError().body(new MyResponse("KO","nell'inserimento " + e.getMessage() ));
        }
    }
    @PostMapping(value = "/eto", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insert(@RequestBody PostEto post) {
        try {
            postService.insertPost(post);
            return ResponseEntity.ok().body(new MyResponse("OK"));
        } catch (PostException e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new MyResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("errore nell'inserimento del post", e);
            return ResponseEntity.internalServerError().body(new MyResponse("KO","nell'inserimento " + e.getMessage() ));
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody PostDto post) {
        try {
            postService.updatePost(post);
            return ResponseEntity.ok().body(new MyResponse("OK"));
        } catch (Exception e) {
            logger.error("errore nell'update del post", e);
            return ResponseEntity.internalServerError().body(new MyResponse("KO","errore nell'update del post " + e.getMessage() ));
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") Long  id) {
        try {
            postService.delete(id);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
