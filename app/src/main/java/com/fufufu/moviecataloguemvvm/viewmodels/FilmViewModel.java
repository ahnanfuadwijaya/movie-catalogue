package com.fufufu.moviecataloguemvvm.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;

import java.util.ArrayList;

public class FilmViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Film>> currentFilmList;
    private Repository repository;

    public FilmViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }


    public LiveData<ArrayList<Film>> getFilmList() {
        String langPref = "Language";
        SharedPreferences prefs = getApplication().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        String lang = "en";
        if(language.equalsIgnoreCase("en")){
            lang = "en-US";
        }
        else if(language.equalsIgnoreCase("in")){
            lang = "id-ID";
        }
        if (currentFilmList == null) {
            currentFilmList = repository.getFilmListFromApi("f240487696509310687e5998a34a405f", lang, "popularity.desc");
        }
        return currentFilmList;
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
