package com.fufufu.moviecatalogue.dao;

import android.database.Cursor;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;

@Dao
public interface FavoriteTvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavoriteTvShow(FavoriteTvShow favoriteTvShow);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllFavoriteTvShows(FavoriteTvShow[] favoriteTvShows);

    @Query("SELECT * FROM favorite_tv_show_table")
    Cursor getAllFavoriteTvShows();

    @Query("SELECT * FROM favorite_tv_show_table WHERE favorite_tv_show_table.id IS :id")
    Cursor getTvShow(long id);

    @Update
    int updateFavoriteTvShow(FavoriteTvShow favoriteTvShow);

    @Query("DELETE FROM favorite_tv_show_table WHERE id = :id")
    int deleteFavoriteTvShow(long id);

    @Query("DELETE FROM favorite_tv_show_table")
    void deleteAllFavoriteTvShow();
}
