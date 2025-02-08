package com.example.zapcart.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitClient = null;

    private RetrofitClient() {

    }

    public static ProductAPIService getProductAPIService(){
        if(retrofitClient == null){
            retrofitClient = new Retrofit.Builder()
                    .baseUrl("https://dummyjson.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        //Creates and returns an implementation of the ProductAPIService interface using create(ProductAPIService.class).
        return retrofitClient.create(ProductAPIService.class);
    }
}
