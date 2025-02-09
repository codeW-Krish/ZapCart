package com.example.zapcart.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public UserViewModelFactory(Application application){
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelCls){
        if(modelCls.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
