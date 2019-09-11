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

@Database(entities = {FavoriteFilm.class}, version = 1, exportSchema = false)
public abstract class FavoriteFilmDatabase extends RoomDatabase {
    private static FavoriteFilmDatabase instance;

    public abstract FavoriteFilmDao favoriteFilmDao();

    public static synchronized FavoriteFilmDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, FavoriteFilmDatabase.class, "favorite_film_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsync(instance).execute();
        }
    };

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        private PopulateDatabaseAsync(FavoriteFilmDatabase database) {
            this.favoriteFilmDao = database.favoriteFilmDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFilmDao.getAllFavoriteFilms();
            return null;
        }
    }
}
