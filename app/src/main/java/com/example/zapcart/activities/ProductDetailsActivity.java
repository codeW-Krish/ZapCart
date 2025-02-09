package com.example.zapcart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.zapcart.R;
import com.example.zapcart.adapter.ProductDetailsAdapter;
import com.example.zapcart.authentication.LogInActivity;
import com.example.zapcart.authentication.SessionManager;
import com.example.zapcart.database.CartEntity;
import com.example.zapcart.repositories.CartRepository;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    ViewPager viewPager;
    ProductDetailsAdapter productDetailsAdapter;
    List<String> imageUrls;
    TextView product_title,product_description,product_ratings,product_price;
    Button add_to_cart;
    ImageButton go_to_cart;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);

        viewPager = findViewById(R.id.image_slider_pager);
        product_title = findViewById(R.id.product_details_title);
        product_description = findViewById(R.id.product_details_description);
        product_ratings = findViewById(R.id.product_details_rating);
        product_price = findViewById(R.id.product_details_price);
        add_to_cart = findViewById(R.id.btn_add_to_cart);
        go_to_cart = findViewById(R.id.go_to_cart);
        sessionManager = new SessionManager(this);

        imageUrls = getIntent().getStringArrayListExtra("product_images");
        int product_id = getIntent().getIntExtra("product_id",-1);
        String thumbnail = getIntent().getStringExtra("product_thumbnail");
        String title = getIntent().getStringExtra("product_title");
        String description = getIntent().getStringExtra("product_description");
        Double price = getIntent().getDoubleExtra("product_price",0.0);
        Double rating = getIntent().getDoubleExtra("product_rating",0);

        productDetailsAdapter = new ProductDetailsAdapter(this,imageUrls);
        viewPager.setAdapter(productDetailsAdapter);

        product_title.setText(title);
        product_description.setText(description);
        product_price.setText("Price: "+String.valueOf(price)+"$");
        product_ratings.setText("Ratings: "+String.valueOf(rating));

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn()){
                    CartEntity item = new CartEntity(product_id,title,price,1,thumbnail);
                    CartRepository cartRepository = new CartRepository(getApplicationContext());
                    cartRepository.addToCart(item);
                    Intent intent = new Intent(ProductDetailsActivity.this,MainActivity.class);
                    intent.putExtra("is_from_logged_in","YES");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ProductDetailsActivity.this, LogInActivity.class);
                    intent.putExtra("from_add_to_cart","YES");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}