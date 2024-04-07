package com.catalinacatau.petshop.controllers;

import com.catalinacatau.petshop.dto.ProductDto;
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

    @GetMapping("/cart")
    public ResponseEntity<?> getAllShoppingCarts() {
        return cartService.getAllShoppingCarts();
    }

    @PostMapping("/cart")
    public ResponseEntity<?> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return cartService.createCart(shoppingCart);
    }

    @GetMapping("/cart/{shoppingCartId}")
    public ResponseEntity<?> getCartById(@PathVariable Long shoppingCartId) {
        return cartService.getCartById(shoppingCartId);
    }

    @PostMapping("/cart/{shoppingCartId}/add-to-cart")
    public ResponseEntity<?> addItemsToCart(@PathVariable Long shoppingCartId, @Valid @RequestBody ProductDto productDto) {
        return cartService.addItemsToCart(shoppingCartId, productDto);
    }

    @DeleteMapping("/cart/{shoppingCartId}/remove-from-cart")
    public ResponseEntity<?> removeItemsFromCart(@PathVariable Long shoppingCartId, @Valid @RequestBody ProductDto productDto) {
        return cartService.removeItemsFromCart(shoppingCartId, productDto);
    }

    @DeleteMapping("/cart/{shoppingCartId}")
    public ResponseEntity<?> clearCartById(@PathVariable Long shoppingCartId) {
        return cartService.clearCartById(shoppingCartId);
    }
}
