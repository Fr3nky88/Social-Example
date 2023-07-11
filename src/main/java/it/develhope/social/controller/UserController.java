package it.develhope.social.controller;

import it.develhope.social.entities.User;
import it.develhope.social.exceptions.UserException;
import it.develhope.social.model.MyResponse;
import it.develhope.social.model.UserDto;
import it.develhope.social.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertBulk(@RequestBody List<UserDto> users) {
        try {
            userService.insertBulk(users);
        } catch (UserException e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new MyResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("",e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insert(@RequestBody UserDto user) {
        try {
            userService.insert(user);
            logger.info("User {}", user);
        } catch (UserException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MyResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("",e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") Long  id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable(value = "id") Long  id, @RequestBody UserDto user) {
        try {
            user.setId(id);
            userService.update(user);
            logger.info("User {}", user);
        } catch (UserException e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new MyResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("",e);
            return ResponseEntity.internalServerError().body(new MyResponse(e.getMessage()));
        }
        return ResponseEntity.ok().body(new MyResponse("OK", "Utente aggiornato correttamente", null));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findByNome(@RequestParam(value = "nome", required=false) String nome,
                                 @RequestParam(value = "cognome", required=false) String cognome,
                                 @RequestParam(value = "email", required=false) String email
    ) {

        if (nome != null) {
            return userService.findContainsLetter(nome);
        } else if (cognome != null) {
            return userService.findByCognome(cognome);
        }
        else if (cognome != null && nome != null) {
            return userService.findByNomeAndByCognome(nome, cognome);
        } else if (email != null) {
            return userService.findByEmail(email);
        } else {
            return userService.getAll();
        }
    }


}
