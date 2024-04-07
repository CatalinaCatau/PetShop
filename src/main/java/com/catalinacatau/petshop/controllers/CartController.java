package com.catalinacatau.petshop.controllers;

import com.catalinacatau.petshop.entities.CartItem;
import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    private CartService cartService;

    public CartController(CartService shoppingCartService) {
        this.cartService = shoppingCartService;
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addItemsToCart(@Valid @RequestBody CartItem cartItem) {
        return cartService.addItemsToCart(cartItem);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return cartService.createCart(shoppingCart);
    }
}
