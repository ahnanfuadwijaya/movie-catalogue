package com.fufufu.moviecataloguemvvm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fufufu.moviecataloguemvvm.dao.FilmDao;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;

@Database(entities = {FavoriteFilm.class}, version = 1)
public abstract class FavoriteFilmDatabase extends RoomDatabase {
    private static FavoriteFilmDatabase instance;
    public abstract FilmDao filmDao();

    public static synchronized FavoriteFilmDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, FavoriteFilmDatabase.class, "favorite_film_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
