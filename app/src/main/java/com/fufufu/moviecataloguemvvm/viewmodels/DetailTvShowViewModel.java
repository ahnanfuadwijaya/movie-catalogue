package com.fufufu.moviecataloguemvvm.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecataloguemvvm.models.Repository;
import com.fufufu.moviecataloguemvvm.models.TvShow;

public class DetailTvShowViewModel extends AndroidViewModel {
    private MutableLiveData<TvShow> tvShow;
    private Repository repository;

    public DetailTvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }


    public MutableLiveData<TvShow> getTvShow(int id) {
        String langPref = "Language";
        SharedPreferences prefs = getApplication().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        String lang = "en";
        if (language.equalsIgnoreCase("en")) {
            lang = "en-US";
        } else if (language.equalsIgnoreCase("in")) {
            lang = "id-ID";
        }
        if (tvShow == null) {
            tvShow = repository.getDetailTvShowFromApi(id, lang);
        }
        return tvShow;
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
