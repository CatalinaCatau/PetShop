package com.catalinacatau.petshop.entities;

public class Product {

    private int id;
    private String name;
    private String category;
    private String type;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, String category, String type, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
