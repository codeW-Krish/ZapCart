package com.example.zapcart.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.module.LibraryGlideModule;
import com.example.zapcart.database.UserEntity;
import com.example.zapcart.repositories.UserRepository;

import java.util.concurrent.Executors;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<UserEntity> userLiveData;
    private MutableLiveData<String> usernameLiveData;

    public  UserViewModel(@NonNull Application application){
        super(application);
        userRepository = new UserRepository(application);
        userLiveData = new MutableLiveData<>();
        usernameLiveData = new MutableLiveData<>();
    }

    public void registerUser(UserEntity user){
        Executors.newSingleThreadExecutor().execute(() -> {
            userRepository.registerUser(user);
        });
    }

    public void loginUser(String email, String password){
        Executors.newSingleThreadExecutor().execute(() -> {
            UserEntity user = userRepository.loginUser(email, password);
            userLiveData.postValue(user);
        });
    }

    public LiveData<UserEntity> getUserLiveData(){
        return userLiveData;
    }

    public boolean isEmailRegistered(String email){
        return userRepository.isEmailRegistered(email);
    }

    public void fetchUserName(String email){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String username = userRepository.getUserNameByEmail(email);
                usernameLiveData.postValue(username);
            }
        });
    }

    public LiveData<String> getUserNameLiveData(){
        return usernameLiveData;
    }
}
