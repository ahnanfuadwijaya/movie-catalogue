package com.fufufu.moviecataloguemvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fufufu.moviecataloguemvvm.models.Repository;
import com.fufufu.moviecataloguemvvm.models.Film;

import java.util.ArrayList;

public class FilmViewModel extends ViewModel{
    // Create a LiveData with a String
    private MutableLiveData<ArrayList<Film>> currentFilmList;
    private Repository repository;

    public FilmViewModel() {
        super();
        repository = new Repository();
    }

    public LiveData<ArrayList<Film>> getFilmList() {
        if (currentFilmList == null) {
            currentFilmList = repository.getFilmListFromApi();
        }
        return currentFilmList;
    }
}
