package com.example.zapcart.api;

import com.example.zapcart.models.Product;
import com.example.zapcart.models.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPIService {



    @GET("products")
     Call<ProductResponse> getProducts(@Query("limit") int limit, @Query("skip") int skip, @Query("select") String select);

    @GET("products/search")
    Call<ProductResponse> searchProducts(@Query("q") String query,@Query("limit") int limit);

    @GET("products/category/{category}")
    Call<ProductResponse> getProductsByCategory(@Path("category") String category);

    @GET("products/categories")
    Call<List<String>> getCategoryList();
}
