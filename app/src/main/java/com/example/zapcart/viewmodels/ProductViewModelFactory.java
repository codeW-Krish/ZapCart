package com.example.zapcart.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.zapcart.repositories.ProductRepository;

import java.security.Policy;

public class ProductViewModelFactory implements ViewModelProvider.Factory {
    private final ProductRepository productRepository;


    public ProductViewModelFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(ProductViewModel.class)){
            return (T) new ProductViewModel(productRepository);
        }
        throw new IllegalArgumentException("Unknown View Model Class");
    }
}
