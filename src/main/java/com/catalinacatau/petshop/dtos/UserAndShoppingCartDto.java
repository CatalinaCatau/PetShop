package com.catalinacatau.petshop.dtos;

import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.entities.User;

public class UserAndShoppingCartDto {
    private User user;
    private ShoppingCart shoppingCart;

    public UserAndShoppingCartDto() {
    }

    public UserAndShoppingCartDto(User user, ShoppingCart shoppingCart) {
        this.user = user;
        this.shoppingCart = shoppingCart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
