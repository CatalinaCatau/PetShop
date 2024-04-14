package com.catalinacatau.petshop.services;

import com.catalinacatau.petshop.dtos.UserAndShoppingCartDto;
import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.entities.User;
import com.catalinacatau.petshop.repositories.ShoppingCartRepository;
import com.catalinacatau.petshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shoppingCartRepository = shoppingCartRepository;
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

    public ResponseEntity<?> getUserById(Long id) {
        ResponseEntity<?> response = null;

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByUserId(id);

            if (optionalShoppingCart.isPresent()) {
                UserAndShoppingCartDto responseDto = new UserAndShoppingCartDto(user, optionalShoppingCart.get());

                response = new ResponseEntity<>(responseDto, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> getUserByUsername(String username) {
        ResponseEntity<?> response = null;

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            response = getUserById(optionalUser.get().getId());
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> addUser(User user) {
        ResponseEntity<?> response = null;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            User insertedUser = userRepository.saveAndFlush(user);

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(insertedUser.getId());

            ShoppingCart insertedShoppingCart = shoppingCartRepository.saveAndFlush(shoppingCart);

            UserAndShoppingCartDto responseDto = new UserAndShoppingCartDto(insertedUser, insertedShoppingCart);

            response = new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
