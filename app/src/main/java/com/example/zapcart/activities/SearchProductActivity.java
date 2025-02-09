package com.example.zapcart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zapcart.R;
import com.example.zapcart.adapter.ProductAdapter;
import com.example.zapcart.models.Product;
import com.example.zapcart.repositories.ProductRepository;
import com.example.zapcart.viewmodels.ProductViewModel;
import com.example.zapcart.viewmodels.ProductViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    RecyclerView recyclerView;
    ProgressBar starting_progress_bar, fetching_progress_bar;
    ProductViewModel productViewModel;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_product);

        recyclerView = findViewById(R.id.search_recycler_view);
        starting_progress_bar = findViewById(R.id.starting_progress_bar);
        fetching_progress_bar = findViewById(R.id.fetching_progress_bar);

        productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(new ProductRepository())).get(ProductViewModel.class);
        productAdapter = new ProductAdapter(this,this);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchProductActivity.this,2));
        recyclerView.setAdapter(productAdapter);

        String query = getIntent().getStringExtra("KEY_QUERY");


        productViewModel.getSearchedProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                if(productList != null && !productList.isEmpty()) {
                    starting_progress_bar.setVisibility(View.GONE);
                    productAdapter.setProductList(productList);
                    productAdapter.notifyDataSetChanged();
                }
                fetching_progress_bar.setVisibility(View.GONE);
            }
        });


        starting_progress_bar.setVisibility(View.VISIBLE);
        if(query != null){
            productViewModel.searchProductsNoPagination(query);
        }

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if(!recyclerView.canScrollVertically(1)){
//                    fetching_progress_bar.setVisibility(View.VISIBLE);
//                    productViewModel.searchProducts(query);
//                }
//            }
//        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }


    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putStringArrayListExtra("product_images", (ArrayList<String>) product.images);
        intent.putExtra("product_title", product.title);
        intent.putExtra("product_description", product.description);
        intent.putExtra("product_price", product.price);
        intent.putExtra("product_rating", product.rating);
        startActivity(intent);
    }
}