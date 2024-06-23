package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username) {
        User userInDb = userService.findByUsername(username);
        if (userInDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        User savedUser = userService.saveUser(userInDb);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
