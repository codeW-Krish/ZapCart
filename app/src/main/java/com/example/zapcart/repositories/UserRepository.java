package com.example.zapcart.repositories;

import android.content.Context;

import com.example.zapcart.authentication.AppDataBase;
import com.example.zapcart.database.UserDAO;
import com.example.zapcart.database.UserEntity;

import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private UserDAO userDAO;

    public UserRepository(Context context){
        AppDataBase db = AppDataBase.getInstance(context);
        userDAO = db.userDAO();
    }

    public void registerUser(UserEntity user){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDAO.insertUser(user);
            }
        }).start();
    }

    public UserEntity loginUser(String email, String password){
        return userDAO.login(email,password);
    }

    public boolean isEmailRegistered(String email){
        return userDAO.getUserByEmail(email) != null;
    }

    public String getUserNameByEmail(String email){
        return userDAO.getUserNameByEmail(email);
    }
}
