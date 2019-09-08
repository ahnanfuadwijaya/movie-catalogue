package com.fufufu.moviecataloguemvvm.models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fufufu.moviecataloguemvvm.dao.FavoriteFilmDao;
import com.fufufu.moviecataloguemvvm.database.FavoriteFilmDatabase;

import java.util.List;

public class FavoriteFilmRepository {
    private FavoriteFilmDao favoriteFilmDao;
    private LiveData<List<FavoriteFilm>> favoriteFilmList;

    public FavoriteFilmRepository(Application application){
        FavoriteFilmDatabase favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(application);
        favoriteFilmDao = favoriteFilmDatabase.favoriteFilmDao();
        favoriteFilmList = favoriteFilmDao.getAllFavoriteFilms();
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm){
        new InsertFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm){
        new UpdateFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void deleteFavoriteFilm(FavoriteFilm favoriteFilm){
        new DeleteFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public LiveData<List<FavoriteFilm>> getAllFavoriteFilms(){
        return favoriteFilmList;
    }

    public void deleteAllFavoriteFilms(){
        new DeleteAllFavoriteFilmsAsyncTask(favoriteFilmDao).execute();
    }

    private static class InsertFavoriteFilmAsyncTask extends AsyncTask<FavoriteFilm, Void, Void>{
        private FavoriteFilmDao favoriteFilmDao;

        InsertFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao){
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(FavoriteFilm... favoriteFilms) {
            favoriteFilmDao.insertFavoriteFilm(favoriteFilms[0]);
            return null;
        }
    }

    private static class GetAllFavoriteFilmsAsyncTask extends AsyncTask<Void, Void, Void>{
        private FavoriteFilmDao favoriteFilmDao;

        GetAllFavoriteFilmsAsyncTask(FavoriteFilmDao favoriteFilmDao){
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFilmDao.getAllFavoriteFilms();
            return null;
        }
    }

    private static class UpdateFavoriteFilmAsyncTask extends AsyncTask<FavoriteFilm, Void, Void>{
        private FavoriteFilmDao favoriteFilmDao;

        UpdateFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao){
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(FavoriteFilm... favoriteFilms) {
            favoriteFilmDao.updateFavoriteFilm(favoriteFilms[0]);
            return null;
        }
    }

    private static class DeleteFavoriteFilmAsyncTask extends AsyncTask<FavoriteFilm, Void, Void>{
        private FavoriteFilmDao favoriteFilmDao;

        DeleteFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao){
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(FavoriteFilm... favoriteFilms) {
            favoriteFilmDao.deleteFavoriteFilm(favoriteFilms[0]);
            return null;
        }
    }

    private static class DeleteAllFavoriteFilmsAsyncTask extends AsyncTask<Void, Void, Void>{
        private FavoriteFilmDao favoriteFilmDao;

        DeleteAllFavoriteFilmsAsyncTask(FavoriteFilmDao favoriteFilmDao){
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFilmDao.deleteAllFavoriteFilms();
            return null;
        }
    }
}
