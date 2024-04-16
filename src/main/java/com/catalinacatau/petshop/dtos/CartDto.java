package com.catalinacatau.petshop.dtos;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
    private List<CartItemDto> cartItems = new ArrayList<>();
    private Double totalCost;

    public CartDto() {
    }

    public CartDto(List<CartItemDto> cartItems, Double totalCost) {
        this.cartItems = cartItems;
        this.totalCost = totalCost;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void addCartItem(CartItemDto cartItemDto) {
        this.cartItems.add(cartItemDto);
    }
}
