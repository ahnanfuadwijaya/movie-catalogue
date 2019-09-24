package com.fufufu.favoritefilm.viewmodels;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.repository.Repository;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;


public class FavoriteFilmViewModel extends AndroidViewModel {
    private Repository favoriteFilmRepository;
    private Cursor allFavoriteFilms;
    private MutableLiveData<ArrayList<FavoriteFilm>> allFavoriteFilmsLiveData = new MutableLiveData<>();

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

    public void deleteFavoriteFilm(Long id) {
        favoriteFilmRepository.deleteFavoriteFilm(id);
    }

    public void deleteAllFavoriteFilms() {
        favoriteFilmRepository.deleteAllFavoriteFilms();
    }

    public LiveData<Boolean> isLoading() {
        return favoriteFilmRepository.getLoading();
    }

    public LiveData<ArrayList<FavoriteFilm>> getAllFavoriteFilmsLiveData() {
        return allFavoriteFilmsLiveData;
    }

    public void setAllFavoriteFilmsLiveData(Cursor cursor) {
        this.allFavoriteFilmsLiveData.setValue(mapCursorToArrayList(cursor));
    }

    private static ArrayList<FavoriteFilm> mapCursorToArrayList(Cursor cursor){
        ArrayList<FavoriteFilm> favoriteFilms = new ArrayList<>();
        while (cursor.moveToFirst()){
            long id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
            Float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("voteAverage"));
            favoriteFilms.add(new FavoriteFilm(id, posterPath, title, voteAverage));
        }
        return favoriteFilms;
    }
}