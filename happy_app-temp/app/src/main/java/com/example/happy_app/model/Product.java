package com.example.happy_app.model;

public class Product {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String description;

    public Product(Long id, String name, int price, int stockQuantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public Long getId() {
        return id;
    }

}
