package com.fufufu.moviecataloguemvvm.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilmRepository;

import java.util.List;

public class FavoriteFilmViewModel extends AndroidViewModel{
    private FavoriteFilmRepository favoriteFilmRepository;
    private LiveData<List<FavoriteFilm>> allFavoriteFilms;

    public FavoriteFilmViewModel (Application application) {
        super(application);
        favoriteFilmRepository = new FavoriteFilmRepository(application);
        allFavoriteFilms = favoriteFilmRepository.getAllFavoriteFilms();
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm){
        favoriteFilmRepository.insertFavoriteFilm(favoriteFilm);
    }

    public LiveData<List<FavoriteFilm>> getAllFavoriteFilms(){
        return allFavoriteFilms;
    }

    public FavoriteFilm getFavoriteFilm(int id){
        return favoriteFilmRepository.getFavoriteFilm(id);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm){
        favoriteFilmRepository.updateFavoriteFilm(favoriteFilm);
    }

    public void deleteFavoriteFilm(FavoriteFilm favoriteFilm){
        favoriteFilmRepository.deleteFavoriteFilm(favoriteFilm);
    }

    public void deleteAllFavoriteFilms(){
        favoriteFilmRepository.deleteAllFavoriteFilms();
    }

    public LiveData<Boolean> isLoading() {
        return favoriteFilmRepository.getLoading();
    }
}