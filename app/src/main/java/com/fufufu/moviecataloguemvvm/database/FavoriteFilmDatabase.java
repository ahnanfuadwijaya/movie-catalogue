package com.fufufu.moviecataloguemvvm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fufufu.moviecataloguemvvm.dao.FavoriteFilmDao;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;

@Database(entities = {FavoriteFilm.class}, version = 1)
public abstract class FavoriteFilmDatabase extends RoomDatabase {
    private static FavoriteFilmDatabase instance;
    public abstract FavoriteFilmDao favoriteFilmDao();

    public static synchronized FavoriteFilmDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, FavoriteFilmDatabase.class, "favorite_film_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsync(instance).execute();
        }
    };

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void>{
        private FavoriteFilmDao favoriteFilmDao;

        private PopulateDatabaseAsync(FavoriteFilmDatabase database){
            this.favoriteFilmDao = database.favoriteFilmDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFilmDao.insertFavoriteFilm(new FavoriteFilm(1, "PosterPath 1", "Overview1", "01-01-1111", "Title 1", "Original Title 1", 8.1f));
            favoriteFilmDao.insertFavoriteFilm(new FavoriteFilm(2, "PosterPath 2", "Overview2", "02-01-1111", "Title 2", "Original Title 2", 8.2f));
            favoriteFilmDao.insertFavoriteFilm(new FavoriteFilm(3, "PosterPath 3", "Overview3", "03-01-1111", "Title 3", "Original Title 3", 8.3f));
            return null;
        }
    }
}
