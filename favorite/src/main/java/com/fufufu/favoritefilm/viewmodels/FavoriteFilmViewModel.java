package com.fufufu.favoritefilm.viewmodels;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.repository.Repository;

import java.util.List;

public class FavoriteFilmViewModel extends AndroidViewModel {
    private Repository favoriteFilmRepository;
    private Cursor allFavoriteFilms;

    public FavoriteFilmViewModel(Application application) {
        super(application);
        favoriteFilmRepository = new Repository(application);
        allFavoriteFilms = favoriteFilmRepository.getAllFavoriteFilms();
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm) {
        favoriteFilmRepository.insertFavoriteFilm(favoriteFilm);
    }

    public Cursor getAllFavoriteFilms() {
        return allFavoriteFilms;
    }

    public Cursor getFavoriteFilm(int id) {
        return favoriteFilmRepository.getFavoriteFilm(id);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm) {
        favoriteFilmRepository.updateFavoriteFilm(favoriteFilm);
    }

    public void deleteFavoriteFilm(FavoriteFilm favoriteFilm) {
        favoriteFilmRepository.deleteFavoriteFilm(favoriteFilm);
    }

    public void deleteAllFavoriteFilms() {
        favoriteFilmRepository.deleteAllFavoriteFilms();
    }

    public LiveData<Boolean> isLoading() {
        return favoriteFilmRepository.getLoading();
    }
}