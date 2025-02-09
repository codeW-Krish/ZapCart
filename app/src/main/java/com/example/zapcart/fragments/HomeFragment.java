package com.example.zapcart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.zapcart.R;
import com.example.zapcart.activities.ProductDetailsActivity;
import com.example.zapcart.activities.SearchProductActivity;
import com.example.zapcart.adapter.ProductAdapter;
import com.example.zapcart.api.RetrofitClient;
import com.example.zapcart.models.Product;
import com.example.zapcart.models.ProductResponse;
import com.example.zapcart.repositories.ProductRepository;
import com.example.zapcart.viewmodels.ProductViewModel;
import com.example.zapcart.viewmodels.ProductViewModelFactory;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;
    private ProgressBar progressBar;
    private SearchView searchView;

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

        searchView = view.findViewById(R.id.search_view);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        productAdapter = new ProductAdapter(getContext(), HomeFragment.this);
        recyclerView.setAdapter(productAdapter);

        productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(new ProductRepository()))
                .get(ProductViewModel.class);


        productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                if (productList != null && !productList.isEmpty()) {
                    productAdapter.setProductList(productList);
                    productAdapter.notifyDataSetChanged();
                    Log.d("HomeFragment", "Products updated in adapter: " + productList.size());
                } else {
                    Log.e("HomeFragment", "No products to display");
                }
                hideProgressBar();
            }
        });

        showProgressBar();

        productViewModel.fetchProducts();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {// Check if we're at the bottom
                    showProgressBar();
                    productViewModel.fetchProducts(); // Fetch more products when scrolled to bottom
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    return false;
                }
                Intent intent = new Intent(getContext(), SearchProductActivity.class);
                intent.putExtra("KEY_QUERY",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }


        @Override
        public void onProductClick(Product product) {
            Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
            intent.putExtra("product_id",product.id);
            intent.putStringArrayListExtra("product_images", (ArrayList<String>) product.images);
            intent.putExtra("product_title", product.title);
            intent.putExtra("product_description", product.description);
            intent.putExtra("product_price", product.price);
            intent.putExtra("product_rating", product.rating);
            intent.putExtra("product_thumbnail",product.thumbnail);
            startActivity(intent);
        }

        private void showProgressBar(){
            if(progressBar != null){
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        private void hideProgressBar(){
            if(progressBar != null){
                progressBar.setVisibility(View.GONE);
            }
        }
    }
