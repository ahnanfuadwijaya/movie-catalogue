package com.fufufu.favoritefilm.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.favoritefilm.dao.FavoriteFilmDao;
import com.fufufu.favoritefilm.dao.FavoriteTvShowDao;
import com.fufufu.favoritefilm.database.FavoriteFilmDatabase;
import com.fufufu.favoritefilm.database.FavoriteTvShowDatabase;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.models.FavoriteTvShow;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private FavoriteFilmDao favoriteFilmDao;
    private Cursor favoriteFilmList;
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> favoriteTvShowList;
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public Repository(Application application) {
        FavoriteFilmDatabase favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(application);

        favoriteFilmDao = favoriteFilmDatabase.favoriteFilmDao();
        favoriteFilmList = favoriteFilmDao.getAllFavoriteFilms();
        FavoriteTvShowDatabase favoriteTvShowDatabase = FavoriteTvShowDatabase.getInstance(application);

        favoriteTvShowDao = favoriteTvShowDatabase.favoriteTvShowDao();
        favoriteTvShowList = favoriteTvShowDao.getAllFavoriteTvShows();
        mutableIsLoading.setValue(true);
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm) {
        new InsertFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm) {
        new UpdateFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void deleteFavoriteFilm(FavoriteFilm favoriteFilm) {
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
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
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

    private static class DeleteFavoriteFilmAsyncTask extends AsyncTask<FavoriteFilm, Void, Void> {
        private FavoriteFilmDao favoriteFilmDao;

        DeleteFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Void doInBackground(FavoriteFilm... favoriteFilms) {
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

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new InsertFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new UpdateFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public void deleteFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new DeleteFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows() {
        mutableIsLoading.setValue(false);
        return favoriteTvShowList;
    }

    public FavoriteTvShow getFavoriteTvShow(int id) {
        mutableIsLoading.setValue(true);
        try {
            return new GetFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllFavoriteTvShows() {
        new DeleteAllFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute();
    }

    private static class InsertFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        InsertFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... favoriteTvShows) {
            favoriteTvShowDao.insertFavoriteTvShow(favoriteTvShows[0]);
            return null;
        }
    }

    private static class GetAllFavoriteTvShowsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        GetAllFavoriteTvShowsAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteTvShowDao.getAllFavoriteTvShows();
            return null;
        }
    }

    private static class GetFavoriteTvShowsAsyncTask extends AsyncTask<Integer, Void, FavoriteTvShow> {
        private FavoriteTvShowDao favoriteTvShowDao;

        GetFavoriteTvShowsAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected FavoriteTvShow doInBackground(Integer... integers) {
            return favoriteTvShowDao.getTvShow(integers[0]);
        }

        @Override
        protected void onPostExecute(FavoriteTvShow favoriteTvShow) {
            super.onPostExecute(favoriteTvShow);
            mutableIsLoading.postValue(false);
        }
    }

    private static class UpdateFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        UpdateFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... favoriteTvShows) {
            favoriteTvShowDao.updateFavoriteTvShow(favoriteTvShows[0]);
            return null;
        }
    }

    private static class DeleteFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        DeleteFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... favoriteTvShows) {
            favoriteTvShowDao.deleteFavoriteTvShow(favoriteTvShows[0]);
            return null;
        }
    }

    private static class DeleteAllFavoriteTvShowsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        DeleteAllFavoriteTvShowsAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteTvShowDao.deleteAllFavoriteTvShow();
            return null;
        }
    }
}