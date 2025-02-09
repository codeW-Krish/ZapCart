package com.example.zapcart.repositories;

import android.service.autofill.LuhnChecksumValidator;
import android.util.Log;

import androidx.lifecycle.LiveData;
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
                    }else {
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

    public MutableLiveData<List<Product>> searchProducts(String query,int limit,int skip){
        MutableLiveData<List<Product>> searchProductsLiveData = new MutableLiveData<>();
        Call<List<String>> categoriesCall = productAPIService.getCategoryList();
        categoriesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<String> categories = response.body();
                    Log.d("ProductRepository", String.valueOf(categories));
                    if(categories.contains(query)){
                        Call<ProductResponse> productResponseCall = productAPIService.getProductsByCategory(query,limit,skip);

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
                    }
                }else{
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
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // if the category list call fails then call the general API to search
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
            }
        });
        return searchProductsLiveData;
    }


public MutableLiveData<List<Product>> searchProductsNoPagination(String query) {
    MutableLiveData<List<Product>> searchProductsLiveData = new MutableLiveData<>();
    Call<List<String>> categoriesCall = productAPIService.getCategoryList();
    categoriesCall.enqueue(new Callback<List<String>>() {
        @Override
        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<String> categories = response.body();
                Log.d("ProductRepository", "Categories: " + categories);
                String queryLowerCase = query.trim().toLowerCase().replace(" ","-");
                Log.d("ProductRepository", "User Query: " + queryLowerCase);

                boolean categoryMatched = false;
                for (String category : categories) {
                    String categoryTrimmed = category.trim().toLowerCase();
                    if (categoryTrimmed.equals(queryLowerCase)) {
                        categoryMatched = true;
                        break;
                    }
                }

                if (categoryMatched) {
                    Log.d("ProductRepository", "Category matched: " + queryLowerCase);
                    fetchProductsByCategory(queryLowerCase, searchProductsLiveData);
                } else {
                    Log.d("ProductRepository", "Category not matched, searching products: " + queryLowerCase);
                    fetchProductByQueryNoPagination(queryLowerCase, searchProductsLiveData);
                }
            } else {
                Log.d("ProductRepository", "Failed to get categories, fetching products directly");
                fetchProductByQueryNoPagination(query, searchProductsLiveData);
            }
        }

        @Override
        public void onFailure(Call<List<String>> call, Throwable t) {
            Log.d("ProductRepository", "Category list fetch failed: " + t.getMessage());
            fetchProductByQueryNoPagination(query, searchProductsLiveData);
        }
    });
    return searchProductsLiveData;
}

private void fetchProductByQueryNoPagination(String query, MutableLiveData<List<Product>> ProductsLiveData) {
    Log.d("ProductRepository", "Fetching products by search query: " + query);
    Call<ProductResponse> productResponseCall = productAPIService.searchProductsNoPagination(query);
    productResponseCall.enqueue(new Callback<ProductResponse>() {
        @Override
        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                Log.d("ProductRepository", "Products by search query fetched: " + response.body().getProducts());
                ProductsLiveData.postValue(response.body().getProducts());
            } else {
                Log.d("ProductRepository", "Failed to fetch products by search query");
                ProductsLiveData.postValue(null);
            }
        }

        @Override
        public void onFailure(Call<ProductResponse> call, Throwable t) {
            Log.d("ProductRepository", "Fetch products by search query failed: " + t.getMessage());
            ProductsLiveData.postValue(null);
        }
    });
}

private void fetchProductsByCategory(String query, MutableLiveData<List<Product>> ProductsLiveData) {
    Log.d("ProductRepository", "Fetching products by category: " + query);
    Call<ProductResponse> productResponseCall = productAPIService.getProductsByCategoryNoPagination(query);
    productResponseCall.enqueue(new Callback<ProductResponse>() {
        @Override
        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                Log.d("ProductRepository", "Products by category fetched: " + response.body().getProducts());
                ProductsLiveData.postValue(response.body().getProducts());
            } else {
                Log.d("ProductRepository", "Failed to fetch products by category");
                ProductsLiveData.postValue(null);
            }
        }

        @Override
        public void onFailure(Call<ProductResponse> call, Throwable t) {
            Log.d("ProductRepository", "Fetch products by category failed: " + t.getMessage());
            ProductsLiveData.postValue(null);
        }
    });
}

//    public Call<ProductResponse> getProducts(int limit,int skip,String select){
//        return productAPIService.getProducts(limit,skip,select);
//    }
//
//    public Call<ProductResponse> searchProducts(String query,int limit){
//        return productAPIService.searchProducts(query,limit);
//    }

}
