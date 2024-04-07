package com.catalinacatau.petshop.service;

import com.catalinacatau.petshop.entities.CartItem;
import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.repository.CartItemRepository;
import com.catalinacatau.petshop.repository.ProductRepository;
import com.catalinacatau.petshop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private ShoppingCartRepository shoppingCartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    @Autowired
    public CartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getCartById(Long id){
        ResponseEntity<?> response = null;
        List<CartItem> shoppingCart = cartItemRepository.findByShoppingCartId(id);

        if (!shoppingCart.isEmpty()) {
            response = new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> addItemsToCart(CartItem cartItem) {
        ResponseEntity<?> response = null;

        try {
            CartItem insertedCartItem = cartItemRepository.saveAndFlush(cartItem);
            response = new ResponseEntity<>(insertedCartItem, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<?> createCart(ShoppingCart shoppingCart) {
        ResponseEntity<?> response = null;

        try {
            ShoppingCart createdShoppingCart = shoppingCartRepository.saveAndFlush(shoppingCart);
            response = new ResponseEntity<>(createdShoppingCart, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
