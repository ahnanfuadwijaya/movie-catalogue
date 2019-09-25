package com.fufufu.favoritefilm.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fufufu.favoritefilm.dao.FavoriteFilmDao;
import com.fufufu.favoritefilm.models.FavoriteFilm;

@Database(entities = {FavoriteFilm.class}, version = 1, exportSchema = false)
public abstract class FavoriteFilmDatabase extends RoomDatabase {
    private static FavoriteFilmDatabase instance;

    public abstract FavoriteFilmDao favoriteFilmDao();

    public static synchronized FavoriteFilmDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteFilmDatabase.class, "favorite_film_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            Log.d("instance DB", "null");
        }
        Log.d("DB getInstance", "Executed");
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("onCreate callback", "executed");
            new PopulateDatabaseAsync(instance).execute();
        }
    };

    @Override
    public void close() {
        super.close();
    }

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        private PopulateDatabaseAsync(FavoriteFilmDatabase database) {
            this.favoriteFilmDao = database.favoriteFilmDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FavoriteFilm favoriteFilm = new FavoriteFilm();
            favoriteFilm.setId(1234);
            favoriteFilm.setTitle("Tes Favorite");
            favoriteFilm.setPosterPath("sdfhsfh");
            favoriteFilm.setVoteAverage(7.0f);
            favoriteFilmDao.insertFavoriteFilm(favoriteFilm);
            Log.d("Populate DB", "executed");
            return null;
        }
    }
}
