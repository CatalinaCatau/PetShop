package com.catalinacatau.petshop.dto;

import jakarta.validation.constraints.NotNull;

public class ProductDto {
    @NotNull
    private Long id;
    @NotNull
    private Integer quantity;

    public ProductDto() {
    }

    public ProductDto(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
