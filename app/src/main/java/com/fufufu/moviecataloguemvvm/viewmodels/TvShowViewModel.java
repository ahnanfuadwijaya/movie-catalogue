package com.fufufu.moviecataloguemvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fufufu.moviecataloguemvvm.models.Repository;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.TvShow;

import java.util.ArrayList;

public class TvShowViewModel extends ViewModel{
    // Create a LiveData with a String
    private MutableLiveData<ArrayList<TvShow>> currentTvShowList;
    private Repository repository;

    public TvShowViewModel() {
        super();
        repository = new Repository();
    }

    public LiveData<ArrayList<TvShow>> getTvShowList() {
        if (currentTvShowList == null) {
            currentTvShowList = repository.getTvShowListFromApi();
        }
        return currentTvShowList;
    }
}
