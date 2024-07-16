package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PublicController {

    //    private static final Logger log = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck() {
        log.info("Health OK");
        log.error("Health OK");
        return "ok";
    }

    @CrossOrigin
    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = null;
        try {
            savedUser = userService.saveNewUser(user);
        } catch (Exception e) {
//            log.info(e.getMessage());
//            log.error("Error occured with username: {}", user.getUsername(), e);
            log.error("eeeeeeeeeeeeeee");
            log.info("iiiiiiiiiiiiiii");
            log.debug("dddddddddddddd");
            log.warn("wwwwwwwwwwwwwwww");
            log.trace("ttttttttttttttt");


            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
