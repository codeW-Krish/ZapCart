package com.example.zapcart.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zapcart.R;
import com.example.zapcart.activities.MainActivity;
import com.example.zapcart.database.UserEntity;
import com.example.zapcart.viewmodels.UserViewModel;
import com.example.zapcart.viewmodels.UserViewModelFactory;

import java.util.concurrent.Executors;

public class LogInActivity extends AppCompatActivity {

    EditText et_email,et_password;
    Button btn_login;
    TextView btn_go_to_register,skip;
    UserViewModel userViewModel;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.button_login);
        btn_go_to_register = findViewById(R.id.btn_go_to_register);
        skip = findViewById(R.id.skip);


        sessionManager = new SessionManager(this);
        UserViewModelFactory factory = new UserViewModelFactory(getApplication());
        userViewModel = new ViewModelProvider(this,factory).get(UserViewModel.class);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

         btn_go_to_register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                 startActivity(intent);
                 finish();
             }
         });

        userViewModel.getUserLiveData().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity user) {
                if(user != null){
                    sessionManager.loginUser();
                    sessionManager.setEmail(et_email.getText().toString());
                    userViewModel.fetchUserName(et_email.getText().toString());
                    navigateToHome();
                }else{
                    Toast.makeText(LogInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userViewModel.getUserNameLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                if(string != null){
                    sessionManager.setUserName(string);
                    navigateToHome();
                }
            }
        });
    }

    private void validateLogin(){
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if(email.isEmpty()){
            et_email.setError("Email Is Required");
            return;
        }

        if(password.isEmpty()){
            et_password.setError("Password is Required");
            return;
        }

        userViewModel.loginUser(email,password);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}