package com.fufufu.moviecatalogue.dao;

import android.database.Cursor;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.fufufu.moviecatalogue.models.FavoriteFilm;

@Dao
public interface FavoriteFilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavoriteFilm(FavoriteFilm favoriteFilm);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllFavoriteFilms(FavoriteFilm[] favoriteFilms);

    @Query("SELECT * FROM favorite_film_table")
    Cursor getAllFavoriteFilms();

    @Query("SELECT * FROM favorite_film_table WHERE favorite_film_table.id IS :id")
    Cursor getFilm(long id);

    @Update
    int updateFavoriteFilm(FavoriteFilm favoriteFilm);

    @Query("DELETE FROM favorite_film_table WHERE id = :id")
    int deleteFavoriteFilm(long id);

    @Query("DELETE FROM favorite_film_table")
    void deleteAllFavoriteFilms();

}
