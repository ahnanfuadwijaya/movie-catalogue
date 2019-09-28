package com.fufufu.favoritefilm.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.favoritefilm.models.Film;
import com.fufufu.favoritefilm.repository.Repository;

public class DetailFilmViewModel extends AndroidViewModel {
    private MutableLiveData<Film> film;
    private Repository repository;

    public DetailFilmViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public MutableLiveData<Film> getFilm(long id) {
        String langPref = "Language";
        SharedPreferences prefs = getApplication().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        String lang = "en";
        if (language.equalsIgnoreCase("en")) {
            lang = "en-US";
        } else if (language.equalsIgnoreCase("in")) {
            lang = "id-ID";
        }
        if (film == null) {
            film = repository.getDetailFilmFromApi(id, lang);
        }
        return film;
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
