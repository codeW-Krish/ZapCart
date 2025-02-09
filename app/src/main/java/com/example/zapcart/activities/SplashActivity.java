package com.example.zapcart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zapcart.R;
import com.example.zapcart.authentication.LogInActivity;
import com.example.zapcart.authentication.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoggesIn = sessionManager.isLoggedIn();
                if(isLoggesIn){
                    navigateToHome();
                }else{
                    navigateToLogin();
                }
            }
        },2000);
    }

    private void navigateToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLogin(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}