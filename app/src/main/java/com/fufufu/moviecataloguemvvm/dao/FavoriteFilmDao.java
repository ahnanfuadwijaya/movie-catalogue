package com.fufufu.moviecataloguemvvm.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;

import java.util.List;

@Dao
public interface FavoriteFilmDao {

    @Insert
    void insertFavoriteFilm(FavoriteFilm favoriteFilm);

    @Query("SELECT * FROM favorite_film_table")
    LiveData<List<FavoriteFilm>> getAllFavoriteFilms();

    @Update
    void updateFavoriteFilm(FavoriteFilm favoriteFilm);

    @Delete
    void deleteFavoriteFilm(FavoriteFilm favoriteFilm);

    @Query("DELETE FROM favorite_film_table")
    void deleteAllFavoriteFilms();

}
