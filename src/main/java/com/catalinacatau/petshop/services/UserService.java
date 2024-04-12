package com.catalinacatau.petshop.services;

import com.catalinacatau.petshop.entities.User;
import com.catalinacatau.petshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
