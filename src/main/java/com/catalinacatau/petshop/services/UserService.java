package com.catalinacatau.petshop.services;

import com.catalinacatau.petshop.entities.User;
import com.catalinacatau.petshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> getUsers() {
        ResponseEntity<?> response = null;

        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(userList, HttpStatus.OK);
        }

        return response;
    }

    public ResponseEntity<?> addUser(User user) {
        ResponseEntity<?> response = null;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            User insertedUser = userRepository.saveAndFlush(user);
            response = new ResponseEntity<>(insertedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
