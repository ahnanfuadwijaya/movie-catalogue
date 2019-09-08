package com.fufufu.moviecataloguemvvm.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.moviecataloguemvvm.models.Repository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFilmViewModel extends AndroidViewModel
{
    public FavoriteFilmViewModel (Application application) {
        super(application);
    }
}