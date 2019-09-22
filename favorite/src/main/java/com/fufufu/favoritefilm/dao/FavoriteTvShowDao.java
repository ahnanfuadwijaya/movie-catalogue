package com.fufufu.favoritefilm.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fufufu.favoritefilm.models.FavoriteTvShow;

import java.util.List;

@Dao
public interface FavoriteTvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow);

    @Query("SELECT * FROM favorite_tv_show_table")
    LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows();

    @Query("SELECT * FROM favorite_tv_show_table WHERE favorite_tv_show_table.id IS :id")
    FavoriteTvShow getTvShow(int id);

    @Update
    void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow);

    @Delete
    void deleteFavoriteTvShow(FavoriteTvShow favoriteTvShow);

    @Query("DELETE FROM favorite_tv_show_table")
    void deleteAllFavoriteTvShow();
}
