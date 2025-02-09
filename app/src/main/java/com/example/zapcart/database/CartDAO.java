package com.example.zapcart.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert()
    void insertCartItem(CartEntity item);

    @Query("SELECT * FROM cart_items")
    List<CartEntity> getAllCartItems();

    @Query("DELETE FROM cart_items WHERE product_id = :product_id")
    void removeCartItem(int product_id);

    @Query("DELETE FROM cart_items")
    void clearCart();
}
