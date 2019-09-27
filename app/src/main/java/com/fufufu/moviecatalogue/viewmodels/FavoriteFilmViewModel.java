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

public class FavoriteFilmViewModel extends AndroidViewModel {
    private FavoriteFilmRepository favoriteFilmRepository;
    private Cursor allFavoriteFilms;
    private MutableLiveData<ArrayList<FavoriteFilm>> allFavoriteFilmsLiveData = new MutableLiveData<>();

    public FavoriteFilmViewModel(Application application) {
        super(application);
        favoriteFilmRepository = new FavoriteFilmRepository(application);
        allFavoriteFilms = favoriteFilmRepository.getAllFavoriteFilms();
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm) {
        favoriteFilmRepository.insertFavoriteFilm(favoriteFilm);
    }

    public LiveData<ArrayList<FavoriteFilm>> getAllFavoriteFilms() {
        mapCursorToArrayList(allFavoriteFilms);
        return allFavoriteFilmsLiveData;
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

    private void mapCursorToArrayList(Cursor cursor){
        ArrayList<FavoriteFilm> favoriteFilms = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                long id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
                Float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("voteAverage"));
                favoriteFilms.add(new FavoriteFilm(id, posterPath, title, voteAverage));
                Log.d("Crsrmapping,item:title", title);
                cursor.moveToNext();
            }while (cursor.moveToNext());
        }
        allFavoriteFilmsLiveData.setValue(favoriteFilms);
    }
}