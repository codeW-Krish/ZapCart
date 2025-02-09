package com.example.zapcart.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.zapcart.database.CartEntity;
import com.example.zapcart.repositories.CartRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class CartViewModel extends AndroidViewModel {

    private CartRepository cartRepository;
    private MutableLiveData<List<CartEntity>> cartItemsLiveData;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
        cartItemsLiveData = new MutableLiveData<>();
    }

    public void addToCart(CartEntity cartItem) {
        Executors.newSingleThreadExecutor().execute(() -> {
            cartRepository.addToCart(cartItem);
            fetchCartItems();
        });
    }

    public void fetchCartItems() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<CartEntity> cartItems = cartRepository.getCartItems();
            cartItemsLiveData.postValue(cartItems);
        });
    }

    public LiveData<List<CartEntity>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public void removeCartItem(int productId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            cartRepository.removeCartItem(productId);
            fetchCartItems();
        });
    }

    public void clearCart() {
        Executors.newSingleThreadExecutor().execute(() -> {
            cartRepository.removeAll();
            fetchCartItems();
        });
    }

}
