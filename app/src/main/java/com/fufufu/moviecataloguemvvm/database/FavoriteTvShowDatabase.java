package com.fufufu.moviecataloguemvvm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fufufu.moviecataloguemvvm.dao.FavoriteFilmDao;
import com.fufufu.moviecataloguemvvm.dao.FavoriteTvShowDao;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.FavoriteTvShow;

@Database(entities = {FavoriteTvShow.class}, version = 1, exportSchema = false)
public abstract class FavoriteTvShowDatabase extends RoomDatabase {
    private static FavoriteTvShowDatabase instance;
    public abstract FavoriteTvShowDao favoriteTvShowDao();

    public static synchronized FavoriteTvShowDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, FavoriteTvShowDatabase.class, "favorite_tv_show_database")
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
            new FavoriteTvShowDatabase.PopulateDatabaseAsync(instance).execute();
        }
    };

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        private PopulateDatabaseAsync(FavoriteTvShowDatabase database){
            this.favoriteTvShowDao = database.favoriteTvShowDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
