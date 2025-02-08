package com.example.zapcart.models;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    public int id;
    public String title;
    public String category;
    public String description;
    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public List<String> images;
    public String thumbnail;

    public Product(int id, String title, String category, String description, double price, double discountPercentage, double rating, int stock, List<String> images, String thumbnail) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.images = images;
        this.thumbnail = thumbnail;
    }
}
