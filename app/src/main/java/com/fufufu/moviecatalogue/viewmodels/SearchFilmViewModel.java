package com.fufufu.moviecatalogue.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecatalogue.models.Film;
import com.fufufu.moviecatalogue.models.Repository;
import java.util.ArrayList;

public class SearchFilmViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Film>> mutableFilmResult;
    private Repository repository;

    public SearchFilmViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void setMutableFilmResult(String lang, String query) {
        this.mutableFilmResult = repository.getFilmResult(lang, query);
    }

    public LiveData<ArrayList<Film>> getFilmResult() {
        return mutableFilmResult;
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
