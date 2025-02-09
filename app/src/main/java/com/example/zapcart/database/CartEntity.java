package com.example.zapcart.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "cart_items")
public class CartEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "product_id")
    int product_id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    public CartEntity(int product_id, String title, double price, int quantity, String thumbnail) {
        this.product_id = product_id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
