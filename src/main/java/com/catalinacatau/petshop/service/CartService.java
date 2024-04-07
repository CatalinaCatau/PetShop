package com.catalinacatau.petshop.service;

import com.catalinacatau.petshop.dto.ProductDto;
import com.catalinacatau.petshop.entities.CartItem;
import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.repository.CartItemRepository;
import com.catalinacatau.petshop.repository.ProductRepository;
import com.catalinacatau.petshop.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<?> getAllShoppingCarts() {
        ResponseEntity<?> response = null;

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();

        if(shoppingCartList.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(shoppingCartList, HttpStatus.OK);
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

    public ResponseEntity<?> addItemsToCart(Long shoppingCartId, ProductDto productDto) {
        ResponseEntity<?> response = null;

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCartId(shoppingCartId);
        cartItem.setProductId(productDto.getId());
        cartItem.setQuantity(productDto.getQuantity());

        try {
            CartItem insertedCartItem = cartItemRepository.saveAndFlush(cartItem);
            response = new ResponseEntity<>(insertedCartItem, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<?> removeItemsFromCart(Long shoppingCartId, ProductDto productDto) {
        ResponseEntity<?> response = null;

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

        return response;
    }

    @Transactional
    public ResponseEntity<?> clearCartById(Long shoppingCartId) {
        cartItemRepository.deleteAllByShoppingCartId(shoppingCartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
