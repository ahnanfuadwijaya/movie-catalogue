package com.fufufu.moviecatalogue.models;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecatalogue.dao.FavoriteFilmDao;
import com.fufufu.moviecatalogue.database.FavoriteFilmDatabase;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteFilmRepository {
    private FavoriteFilmDao favoriteFilmDao;
    private Cursor favoriteFilmList;
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public FavoriteFilmRepository(Application application) {
        FavoriteFilmDatabase favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(application);
        mutableIsLoading.setValue(true);
        favoriteFilmDao = favoriteFilmDatabase.favoriteFilmDao();
        favoriteFilmList = favoriteFilmDao.getAllFavoriteFilms();
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm) {
        new InsertFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm) {
        new UpdateFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void deleteFavoriteFilm(Long favoriteFilm) {
        new DeleteFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public Cursor getAllFavoriteFilms() {
        mutableIsLoading.setValue(false);
        return favoriteFilmList;
    }

    public Cursor getFavoriteFilm(int id) {
        mutableIsLoading.setValue(true);
        try {
            return new GetFavoriteFilmAsyncTask(favoriteFilmDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllFavoriteFilms() {
        new DeleteAllFavoriteFilmsAsyncTask(favoriteFilmDao).execute();
    }

    private static class InsertFavoriteFilmAsyncTask extends AsyncTask<FavoriteFilm, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        InsertFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(FavoriteFilm... favoriteFilms) {
            favoriteFilmDao.insertFavoriteFilm(favoriteFilms[0]);
            return null;
        }
    }

    private static class GetAllFavoriteFilmsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        GetAllFavoriteFilmsAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFilmDao.getAllFavoriteFilms();
            return null;
        }
    }

    private static class GetFavoriteFilmAsyncTask extends AsyncTask<Integer, Void, Cursor> {
        private FavoriteFilmDao favoriteFilmDao;

        GetFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Cursor doInBackground(Integer... integers) {
            return favoriteFilmDao.getFilm(integers[0]);
        }

        @Override
        protected void onPostExecute(Cursor favoriteFilm) {
            super.onPostExecute(favoriteFilm);
            mutableIsLoading.postValue(false);
        }
    }

    private static class UpdateFavoriteFilmAsyncTask extends AsyncTask<FavoriteFilm, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        UpdateFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(FavoriteFilm... favoriteFilms) {
            favoriteFilmDao.updateFavoriteFilm(favoriteFilms[0]);
            return null;
        }
    }

    private static class DeleteFavoriteFilmAsyncTask extends AsyncTask<Long, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        DeleteFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(Long... favoriteFilms) {
            favoriteFilmDao.deleteFavoriteFilm(favoriteFilms[0]);
            return null;
        }
    }

    private static class DeleteAllFavoriteFilmsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        DeleteAllFavoriteFilmsAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFilmDao.deleteAllFavoriteFilms();
            return null;
        }
    }
}
