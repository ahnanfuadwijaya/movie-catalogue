package com.fufufu.moviecatalogue.viewmodels;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.moviecatalogue.models.FavoriteFilm;
import com.fufufu.moviecatalogue.models.FavoriteFilmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteFilmViewModel extends AndroidViewModel {
    private FavoriteFilmRepository favoriteFilmRepository;
    private MutableLiveData<Cursor> allFavoriteFilmsCursorLiveData;

    public FavoriteFilmViewModel(Application application) {
        super(application);
        favoriteFilmRepository = new FavoriteFilmRepository(application);
        allFavoriteFilmsCursorLiveData = favoriteFilmRepository.getFavoriteFilmListCursor();
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm) {
        favoriteFilmRepository.insertFavoriteFilm(favoriteFilm);
    }

    public LiveData<Cursor> getAllFavoriteFilms(){
        return allFavoriteFilmsCursorLiveData;
    }

    public Cursor getFavoriteFilm(long id) {
        return favoriteFilmRepository.getFavoriteFilm(id);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm) {
        favoriteFilmRepository.updateFavoriteFilm(favoriteFilm);
    }

    public void deleteFavoriteFilm(Long favoriteFilm) {
        favoriteFilmRepository.deleteFavoriteFilm(favoriteFilm);
    }

    public void deleteAllFavoriteFilms() {
        favoriteFilmRepository.deleteAllFavoriteFilms();
    }

    public LiveData<Boolean> isLoading() {
        return favoriteFilmRepository.getLoading();
    }
}