package com.fufufu.moviecataloguemvvm.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.fufufu.moviecataloguemvvm.models.Film;
import java.util.List;

@Dao
public interface FilmDao {

    @Insert
    void insertFavoriteFilm(Film film);

    @Query("SELECT * FROM favorite_film_table")
    LiveData<List<Film>> getAllFavoriteFilms();

    @Update
    void updateFavoriteFilm(Film film);

    @Delete
    void deleteFavoriteFilm(Film film);

    @Query("DELETE FROM favorite_film_table")
    void deleteAllFavoriteFilms();

}
