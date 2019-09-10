package com.fufufu.moviecataloguemvvm.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;

public class DetailFilmViewModel extends AndroidViewModel {
    private MutableLiveData<Film> film;
    private Repository repository;

    public DetailFilmViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public MutableLiveData<Film> getFilm(int id) {
        Log.d("getFilm DetFilmVM", "getFilm() executed");
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
        if(film == null){
            film = repository.getDetailFilmFromApi("f240487696509310687e5998a34a405f", id, lang);
        }
        Log.d("setFilm()", "executed ");
        if (film == null){
            Log.d("Film", "Null");
        }
        else {
            Log.d("Film", "Tidak Null");
        }
        return film;
    }

    public void setFilm(int id) {

    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
