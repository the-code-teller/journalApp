package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }


    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public User saveAdmin(User user) {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        return userRepository.save(user);
    }
}
