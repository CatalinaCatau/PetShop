package com.catalinacatau.petshop.services;

import com.catalinacatau.petshop.dtos.ProductDto;
import com.catalinacatau.petshop.entities.CartItem;
import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.entities.User;
import com.catalinacatau.petshop.repositories.CartItemRepository;
import com.catalinacatau.petshop.repositories.ProductRepository;
import com.catalinacatau.petshop.repositories.ShoppingCartRepository;
import com.catalinacatau.petshop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private ShoppingCartRepository shoppingCartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public CartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAllShoppingCarts() {
        ResponseEntity<?> response = null;

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();

        if (shoppingCartList.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(shoppingCartList, HttpStatus.OK);
        }

        return response;
    }

    public ResponseEntity<?> getCartById(Long shoppingCartId) {
        ResponseEntity<?> response = null;
        List<CartItem> shoppingCart = cartItemRepository.findByShoppingCartId(shoppingCartId);

        if (shoppingCart.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }

        return response;
    }

    public ResponseEntity<?> getCart() {
        ResponseEntity<?> response = null;

        Long shoppingCartId = extractShoppingCartIdFromAuthentication();

        if (shoppingCartId == -1) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            List<CartItem> shoppingCart = cartItemRepository.findByShoppingCartId(shoppingCartId);

            if (shoppingCart.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                response = new ResponseEntity<>(shoppingCart, HttpStatus.OK);
            }

        }

        return response;
    }

    public ResponseEntity<?> addItemsToCart(ProductDto productDto) {
        ResponseEntity<?> response = null;
        CartItem cartItem = null;
        Long shoppingCartId = extractShoppingCartIdFromAuthentication();

        if (shoppingCartId == -1) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            Optional<CartItem> optionalCartItem = cartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, productDto.getId());

            if (optionalCartItem.isPresent()) {
                cartItem = optionalCartItem.get();
                int newQuantity = cartItem.getQuantity() + productDto.getQuantity();

                cartItem.setQuantity(newQuantity);
            } else {
                cartItem = new CartItem();
                cartItem.setShoppingCartId(shoppingCartId);
                cartItem.setProductId(productDto.getId());
                cartItem.setQuantity(productDto.getQuantity());
            }

            try {
                cartItem = cartItemRepository.saveAndFlush(cartItem);
                response = new ResponseEntity<>(cartItem, HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return response;
    }

    public ResponseEntity<?> removeItemsFromCart(ProductDto productDto) {
        ResponseEntity<?> response = null;
        Long shoppingCartId = extractShoppingCartIdFromAuthentication();

        if (shoppingCartId == -1) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            Optional<CartItem> optionalCartItem = cartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, productDto.getId());

            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                int newQuantity = cartItem.getQuantity() - productDto.getQuantity();

                if (newQuantity > 0) {
                    cartItem.setQuantity(newQuantity);

                    try {
                        cartItem = cartItemRepository.saveAndFlush(cartItem);
                        response = new ResponseEntity<>(cartItem, HttpStatus.OK);
                    } catch (Exception e) {
                        response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else if (newQuantity == 0) {
                    cartItemRepository.delete(cartItem);
                    response = new ResponseEntity<>(HttpStatus.OK);
                } else {
                    response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return response;
    }

    @Transactional
    public ResponseEntity<?> clearCart() {
        Long shoppingCartId = extractShoppingCartIdFromAuthentication();

        if (shoppingCartId == -1) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        cartItemRepository.deleteAllByShoppingCartId(shoppingCartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Long extractShoppingCartIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByUserId(optionalUser.get().getId());

            if (optionalShoppingCart.isPresent()) {
                return optionalShoppingCart.get().getId();
            }
        }

        return -1L;
    }
}
