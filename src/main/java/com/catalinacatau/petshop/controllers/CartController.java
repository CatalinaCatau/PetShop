package com.catalinacatau.petshop.controllers;

import com.catalinacatau.petshop.dtos.ProductDto;
import com.catalinacatau.petshop.services.CartService;
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

    @GetMapping("/carts")
    public ResponseEntity<?> getAllShoppingCarts() {
        return cartService.getAllShoppingCarts();
    }

    @GetMapping("/carts/{shoppingCartId}")
    public ResponseEntity<?> getCartById(@PathVariable Long shoppingCartId) {
        return cartService.getCartById(shoppingCartId);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {
        return cartService.getCart();
    }

    @PostMapping("/cart/add-to-cart")
    public ResponseEntity<?> addItemsToCart(@Valid @RequestBody ProductDto productDto) {
        return cartService.addItemsToCart(productDto);
    }

    @DeleteMapping("/cart/remove-from-cart")
    public ResponseEntity<?> removeItemsFromCart(@Valid @RequestBody ProductDto productDto) {
        return cartService.removeItemsFromCart(productDto);
    }

    @DeleteMapping("/cart/clear-cart")
    public ResponseEntity<?> clearCart() {
        return cartService.clearCart();
    }
}
