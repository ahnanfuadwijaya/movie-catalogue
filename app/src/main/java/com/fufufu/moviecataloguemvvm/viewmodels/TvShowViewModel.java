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
import java.util.ArrayList;

public class TvShowViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<TvShow>> currentTvShowList;
    private Repository repository;

    public TvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public LiveData<ArrayList<TvShow>> getTvShowList() {
        String langPref = "Language";
        SharedPreferences prefs = getApplication().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        String lang = "en";
        if (language.equalsIgnoreCase("en")) {
            lang = "en-US";
        } else if (language.equalsIgnoreCase("in")) {
            lang = "id-ID";
        }
        if (currentTvShowList == null) {
            currentTvShowList = repository.getTvShowListFromApi(lang, "popularity.desc");
        }
        return currentTvShowList;
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
