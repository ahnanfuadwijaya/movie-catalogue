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
import com.fufufu.moviecataloguemvvm.models.TvShow;

public class DetailTvShowViewModel extends AndroidViewModel {
    private MutableLiveData<TvShow> tvShow;
    private Repository repository;

    public DetailTvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }


    public MutableLiveData<TvShow> getTvShow(int id) {
        Log.d("getTvShow DetTVVM", "getTVShw() executed");
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
        if(tvShow == null){
            tvShow = repository.getDetailTvShowFromApi("f240487696509310687e5998a34a405f", id, lang);
        }
        Log.d("setTV()", "executed ");
        if (tvShow == null){
            Log.d("TvShw", "Null");
        }
        else {
            Log.d("TvShw", "Tidak Null");
        }
        return tvShow;
    }
    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
