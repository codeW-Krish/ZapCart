package com.example.zapcart.authentication;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";

    public SessionManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME,0);
        editor = sharedPreferences.edit();
    }

    public void loginUser(){
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.commit();
    }

    public void setEmail(String email){
        editor.putString(KEY_USER_EMAIL,email);
        editor.commit();
    }

    public String  getUserEmail(){
        return sharedPreferences.getString(KEY_USER_EMAIL,"");
    }

    public void setUserName(String userName){
        editor.putString(KEY_USER_NAME,userName);
        editor.commit();
    }

    public String getUserName(){
        return sharedPreferences.getString(KEY_USER_NAME,"");
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public void setIsLogIn(boolean value){
        editor.putBoolean(KEY_IS_LOGGED_IN,value);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false);
    }

    public String getUserID(){
        return sharedPreferences.getString(KEY_USER_ID,"");
    }
}
