package com.example.zapcart.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(UserEntity user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    UserEntity login(String email,String password);

    @Query("SELECT * FROM users WHERE email = :email")
    UserEntity getUserByEmail(String email);

    @Query("SELECT username FROM users WHERE email = :email")
    String getUserNameByEmail(String email);
}
