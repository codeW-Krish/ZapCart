package com.example.zapcart.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.zapcart.api.ProductAPIService;
import com.example.zapcart.api.RetrofitClient;
import com.example.zapcart.models.Product;
import com.example.zapcart.models.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.Gson;

public class ProductRepository {
    private ProductAPIService productAPIService;

    public ProductRepository(){
        productAPIService = RetrofitClient.getProductAPIService();
    }

    public MutableLiveData<List<Product>> getProducts(int limit, int skip, String select) {
        MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
        Call<ProductResponse> productResponseCall = productAPIService.getProducts(limit,skip,select);

        productResponseCall.enqueue(new Callback<ProductResponse>(){
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Log.d("ProductRepository", "Response Body: " + response.body().toString());
                if(response.isSuccessful()) {
                    Log.d("ProductRepository", "Response Code: " + response.code());
                    Log.d("ProductRepository", "Headers: " + response.headers());
                    if (response.body() != null) {
                        List<Product> productList = response.body().getProducts();
                        Log.d("ProductRepository", "Response Body: " + new Gson().toJson(response.body()));
                        //this we got null why
//                    Log.d("ProductRepository", "Full Response: " + new Gson().toJson(response.body()));
                        if (productList != null && !productList.isEmpty()) {
                            productsLiveData.setValue(productList);
                        } else {
                            productsLiveData.setValue(null);
                            Log.e("ProductRepository", "no response from API in repository");
                        }
                    } else {
                        productsLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

        return productsLiveData;
    }

    public MutableLiveData<List<Product>> searchProducts(String query,int limit){
        MutableLiveData<List<Product>> searchProductsLiveData = new MutableLiveData<>();

        Call<List<String>> categories = productAPIService.getCategoryList();

        Call<ProductResponse> productResponseCall = productAPIService.searchProducts(query,limit);

        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    searchProductsLiveData.setValue(response.body().getProducts());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                searchProductsLiveData.setValue(null);
            }
        });

        return searchProductsLiveData;
    }

}
