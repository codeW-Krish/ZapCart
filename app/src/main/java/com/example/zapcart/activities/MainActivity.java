package com.example.zapcart.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.zapcart.R;
import com.example.zapcart.fragments.CartFragment;
import com.example.zapcart.fragments.HomeFragment;
import com.example.zapcart.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Map<Integer, Fragment> fragmentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.nav_home,new HomeFragment());
        fragmentMap.put(R.id.nav_cart,new CartFragment());
        fragmentMap.put(R.id.nav_profile,new ProfileFragment());

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = fragmentMap.get(item.getItemId());
                if(fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                }
                return true;
            }
        });

    }
}