package com.example.zapcart.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.zapcart.models.Product;
import com.example.zapcart.models.ProductResponse;
import com.example.zapcart.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> productLiveData;
    private MutableLiveData<List<String>> categoriesData;
    private MutableLiveData<List<Product>> searchedProductLiveData;

    private int skip = 0;
    private final int limit = 10;
    private String select = "id,title,category,description,price,discountPercentage,rating,stock,images,thumbnail";
    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productLiveData = new MutableLiveData<>();
        this.categoriesData = new MutableLiveData<>();
        this.searchedProductLiveData = new MutableLiveData<>();
    }


    public LiveData<List<Product>> getProductsLiveData() {
        return productLiveData;
    }


    public LiveData<List<Product>> getSearchedProductsLiveData(){
        return searchedProductLiveData;
    }

    public LiveData<List<String>> getCategoriesLiveData() {
        return categoriesData;
    }

    // Fetch products with pagination
    public void fetchProducts() {
        // Log current skip value for debugging
        Log.d("ProductViewModel", "Fetching products with skip = " + skip);

        // Make API call to get products with current skip and limit
        productRepository.getProducts(limit, skip, select).observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && !products.isEmpty()) {
                    List<Product> currentProducts = productLiveData.getValue();
                    if (currentProducts == null) {
                        currentProducts = new ArrayList<>();
                    }


                    currentProducts.addAll(products);
                    productLiveData.setValue(currentProducts);

                    // Increment skip for the next API call
                    skip += limit;

                    Log.d("ProductViewModel", "Fetched products: " + products.size());
                } else {
                    Log.e("ProductViewModel", "No products fetched or empty response");
                }
            }
        });
    }


//    public void searchProducts(String query) {
//        Log.d("ProductViewModel", "Fetching products with skip = " + skip);
//        productRepository.searchProducts(query, limit,skip).observeForever(products -> {
//            if (products != null && !products.isEmpty()) {
//                List<Product> currentProducts = searchedProductLiveData.getValue();
//                if(currentProducts == null){
//                    currentProducts = new ArrayList<>();
//                }
//
//                currentProducts.addAll(products);
//                searchedProductLiveData.setValue(products);
//                skip += limit;
//                Log.d("ProductViewModel", "Fetched products: " + products.size());
//            }else {
//                Log.e("ProductViewModel", "No products fetched or empty response");
//            }
//        });
//    }

    public void searchProductsNoPagination(String query){
        productRepository.searchProductsNoPagination(query).observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                if(productList != null && !productList.isEmpty()){
                    searchedProductLiveData.setValue(productList);
                }
            }
        });
    }

//    public void fetchProducts(int limit,int skip,String select){
//        Call<ProductResponse> productResponseCall = productRepository.getProducts(limit,skip,select);
//            productResponseCall.enqueue(new Callback<ProductResponse>() {
//                @Override
//                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                    if(response.isSuccessful() && response.body() != null){
//                        productLiveData.setValue(response.body().getProducts());
//                    }else{
//                        productLiveData.setValue(null);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ProductResponse> call, Throwable t) {
//                    productLiveData.setValue(null);
//                }
//            });
//        }
//
//    public void searchProducts(String query,int limit){
//
//    }
}
