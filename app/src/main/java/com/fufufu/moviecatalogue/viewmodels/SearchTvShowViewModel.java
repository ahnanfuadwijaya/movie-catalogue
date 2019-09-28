package com.fufufu.moviecatalogue.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecatalogue.models.Repository;
import com.fufufu.moviecatalogue.models.TvShow;
import java.util.ArrayList;

public class SearchTvShowViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<TvShow>> mutableTvShowResult;
    private Repository repository;

    public SearchTvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void setMutableTvShowResult(String lang, String query) {
        this.mutableTvShowResult = repository.getTvShowResult(lang, query);
    }

    public LiveData<ArrayList<TvShow>> getTvShowmResult() {
        return mutableTvShowResult;
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
