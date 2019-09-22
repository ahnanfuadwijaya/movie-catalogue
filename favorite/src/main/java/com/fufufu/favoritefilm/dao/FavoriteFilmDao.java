package com.fufufu.favoritefilm.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fufufu.favoritefilm.models.FavoriteFilm;

import java.util.List;

@Dao
public interface FavoriteFilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavoriteFilm(FavoriteFilm favoriteFilm);

    @Query("SELECT * FROM favorite_film_table")
    Cursor getAllFavoriteFilms();

    @Query("SELECT * FROM favorite_film_table WHERE favorite_film_table.id IS :id")
    Cursor getFilm(long id);

    @Update
    void updateFavoriteFilm(FavoriteFilm favoriteFilm);

    @Delete
    void deleteFavoriteFilm(FavoriteFilm favoriteFilm);

    @Query("DELETE FROM favorite_film_table")
    void deleteAllFavoriteFilms();

}
