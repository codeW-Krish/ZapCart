package com.example.zapcart.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zapcart.R;
import com.example.zapcart.adapter.ProductAdapter;
import com.example.zapcart.api.RetrofitClient;
import com.example.zapcart.models.Product;
import com.example.zapcart.models.ProductResponse;
import com.example.zapcart.repositories.ProductRepository;
import com.example.zapcart.viewmodels.ProductViewModel;
import com.example.zapcart.viewmodels.ProductViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get parameters if necessary
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        productAdapter = new ProductAdapter(getContext(), HomeFragment.this);
        recyclerView.setAdapter(productAdapter);

//        Call<ProductResponse> productResponseCall = RetrofitClient.getProductAPIService().getProducts(10, 10, "id,title,category,description,price,discountPercentage,rating,stock,images,thumbnail");
//        productResponseCall.enqueue(new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
////                    productAdapter.setProductList(response.body().getProducts());
//                } else {
//                    Log.e("HomeFragment", "API Response was not successful or empty");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//                Log.e("HomeFragment", "API request failed", t);
//            }
//        });

        productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(new ProductRepository()))
                .get(ProductViewModel.class);



        productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                if (productList != null && !productList.isEmpty()) {
                    productAdapter.setProductList(productList);
                    Log.d("HomeFragment", "Products updated in adapter: " + productList.size());
                } else {
                    Log.e("HomeFragment", "No products to display");
                }
            }
        });



        productViewModel.fetchProducts();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) { // Check if we're at the bottom
                    productViewModel.fetchProducts(); // Fetch more products when scrolled to bottom
                }
            }
        });

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        // Handle product click event here if needed
    }
}
