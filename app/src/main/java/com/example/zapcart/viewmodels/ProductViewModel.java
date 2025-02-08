package com.example.zapcart.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.zapcart.models.Product;
import com.example.zapcart.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> productLiveData;
    private MutableLiveData<List<String>> categoriesData;

    private int skip = 0;
    private final int limit = 10;
    private String select = "id,title,category,description,price,discountPercentage,rating,stock,images,thumbnail";
    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productLiveData = new MutableLiveData<>();
        this.categoriesData = new MutableLiveData<>();
    }


    public LiveData<List<Product>> getProductsLiveData() {
        return productLiveData;
    }

    public LiveData<List<String>> getCategoriesLiveData() {
        return categoriesData;
    }

    public void fetchProducts() {
        productRepository.getProducts(limit, skip,select).observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && !products.isEmpty()) {
                    List<Product> currentProducts = productLiveData.getValue();
                    if (currentProducts == null) {
                        currentProducts = new ArrayList<>();
                    }
                    currentProducts.addAll(products);
                    productLiveData.setValue(currentProducts);
                    skip += limit; // Pagination logic
                    Log.d("ProductViewModel", "Fetched products: " + products.size());
                } else {
                    Log.e("ProductViewModel", "No products fetched or empty response");
                }
            }
        });
    }


    public void searchProducts(String query) {
        productRepository.searchProducts(query, limit).observeForever(products -> {
            if (products != null) {
                productLiveData.setValue(products);
            }
        });
    }
}
