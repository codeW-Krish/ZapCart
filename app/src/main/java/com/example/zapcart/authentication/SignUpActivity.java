package com.example.zapcart.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.zapcart.R;
import com.example.zapcart.activities.MainActivity;
import com.example.zapcart.database.UserEntity;
import com.example.zapcart.viewmodels.UserViewModel;
import com.example.zapcart.viewmodels.UserViewModelFactory;

import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    EditText et_name,et_email,et_password,et_confirm_password;
    Button btn_register;
    TextView btn_go_to_login;
    UserViewModel userViewModel;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        btn_register = findViewById(R.id.btn_register);
        btn_go_to_login = findViewById(R.id.btn_go_to_login);

        sessionManager = new SessionManager(this);
        UserViewModelFactory factory = new UserViewModelFactory(getApplication());
        userViewModel = new ViewModelProvider(this,factory).get(UserViewModel.class);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegistration();
            }
        });
    }

    private void validateRegistration(){
        String username = et_name.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirm_password = et_confirm_password.getText().toString();


        if (username.isEmpty()) {
            et_name.setError("Username is required");
            return;
        }

        if (email.isEmpty()) {
            et_email.setError("Email is required");
            return;
        }

        if (password.isEmpty()) {
            et_password.setError("Password is required");
            return;
        }

        if (!password.equals(confirm_password)) {
            et_confirm_password.setError("Passwords do not match");
            return;
        }

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                boolean isEmailRegistered = userViewModel.isEmailRegistered(email);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isEmailRegistered){
                            et_email.setError("Email is Already Registered");
                            Toast.makeText(SignUpActivity.this, "Email is Already Registered", Toast.LENGTH_SHORT).show();
                        }else{
                            UserEntity newUser = new UserEntity(username,email,password);
                            userViewModel.registerUser(newUser);
                            sessionManager.loginUser();
                            sessionManager.setEmail(email);
                            sessionManager.setUserName(username);
                            navigateToHome();
                        }
                    }
                });
            }
        });
    }

    private void navigateToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}