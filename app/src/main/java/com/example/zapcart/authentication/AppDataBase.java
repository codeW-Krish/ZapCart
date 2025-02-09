package com.example.zapcart.authentication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.zapcart.database.CartDAO;
import com.example.zapcart.database.CartEntity;
import com.example.zapcart.database.UserDAO;
import com.example.zapcart.database.UserEntity;


@Database(entities = {UserEntity.class, CartEntity.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase db;
    public abstract UserDAO userDAO();
    public abstract CartDAO cartDAO();

    public static AppDataBase getInstance(Context context){
        if(db == null){
            db = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"APP_DB")
                    .build();
        }
        return db;
    }
}
