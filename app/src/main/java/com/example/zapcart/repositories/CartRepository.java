package com.example.zapcart.repositories;

import android.content.Context;

import com.example.zapcart.authentication.AppDataBase;
import com.example.zapcart.database.CartDAO;
import com.example.zapcart.database.CartEntity;

import java.util.List;

public class CartRepository {
    private CartDAO cartDAO;

    public CartRepository(Context context){
        AppDataBase db = AppDataBase.getInstance(context);
        cartDAO = db.cartDAO();
    }

    public void addToCart(CartEntity item){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cartDAO.insertCartItem(item);
            }
        }).start();
    }

    public List<CartEntity> getCartItems(){
        return cartDAO.getAllCartItems();
    }

    public void removeCartItem(int product_id){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 cartDAO.removeCartItem(product_id);
             }
         }).start();
    }

    public void removeAll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cartDAO.clearCart();
            }
        }).start();
    }
}
