package com.fufufu.moviecatalogue.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;
import com.fufufu.moviecatalogue.models.FavoriteTvShowRepository;
import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private FavoriteTvShowRepository favoriteTvShowRepository;
    private LiveData<List<FavoriteTvShow>> allFavoriteTvShows;

    public FavoriteTvShowViewModel(Application application) {
        super(application);
        favoriteTvShowRepository = new FavoriteTvShowRepository(application);
        allFavoriteTvShows = favoriteTvShowRepository.getAllFavoriteTvShows();
    }

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        favoriteTvShowRepository.insertFavoriteTvShow(favoriteTvShow);
    }

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows() {
        return allFavoriteTvShows;
    }

    public FavoriteTvShow getFavoriteTvShow(int id) {
        return favoriteTvShowRepository.getFavoriteTvShow(id);
    }

    public void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        favoriteTvShowRepository.updateFavoriteTvShow(favoriteTvShow);
    }

    public void deleteFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        favoriteTvShowRepository.deleteFavoriteTvShow(favoriteTvShow);
    }

    public void deleteAllFavoriteTvShows() {
        favoriteTvShowRepository.deleteAllFavoriteTvShows();
    }

    public LiveData<Boolean> isLoading() {
        return favoriteTvShowRepository.getLoading();
    }
}
