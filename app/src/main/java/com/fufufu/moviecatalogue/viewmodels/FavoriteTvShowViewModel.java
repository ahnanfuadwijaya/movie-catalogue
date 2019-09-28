package com.fufufu.moviecatalogue.viewmodels;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.moviecatalogue.models.FavoriteTvShow;
import com.fufufu.moviecatalogue.models.FavoriteTvShowRepository;
import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private FavoriteTvShowRepository favoriteTvShowRepository;
    private MutableLiveData<Cursor> allFavoriteTvShowsCursorLiveData;

    public FavoriteTvShowViewModel(Application application) {
        super(application);
        favoriteTvShowRepository = new FavoriteTvShowRepository(application);
        allFavoriteTvShowsCursorLiveData = favoriteTvShowRepository.getAllFavoriteTvShowCursor();
    }

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        favoriteTvShowRepository.insertFavoriteTvShow(favoriteTvShow);
    }

    public LiveData<Cursor> getAllFavoriteTvShows() {
        return allFavoriteTvShowsCursorLiveData;
    }

    public Cursor getFavoriteTvShow(long id) {
        return favoriteTvShowRepository.getFavoriteTvShow(id);
    }

    public void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        favoriteTvShowRepository.updateFavoriteTvShow(favoriteTvShow);
    }

    public void deleteFavoriteTvShow(long favoriteTvShow) {
        favoriteTvShowRepository.deleteFavoriteTvShow(favoriteTvShow);
    }

    public void deleteAllFavoriteTvShows() {
        favoriteTvShowRepository.deleteAllFavoriteTvShows();
    }

    public LiveData<Boolean> isLoading() {
        return favoriteTvShowRepository.getLoading();
    }
}
