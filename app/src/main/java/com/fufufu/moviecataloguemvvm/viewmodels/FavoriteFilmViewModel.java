package com.fufufu.moviecataloguemvvm.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFilmViewModel extends AndroidViewModel
{
    private Repository repository;
    private MutableLiveData<ArrayList<Film>> favoriteFilms;
    private MutableLiveData<List<Film>> searchResults;

    public FavoriteFilmViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        favoriteFilms = repository.getFavoriteFilm();
        //searchResults = repository.getSearchResults();
    }

    public LiveData<ArrayList<Film>> getFavoriteFilm(){
        return favoriteFilms;
    }
}