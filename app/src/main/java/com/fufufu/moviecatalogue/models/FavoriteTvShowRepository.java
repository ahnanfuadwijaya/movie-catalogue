package com.fufufu.moviecatalogue.models;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecatalogue.dao.FavoriteTvShowDao;
import com.fufufu.moviecatalogue.database.FavoriteTvShowDatabase;
import java.util.concurrent.ExecutionException;

public class FavoriteTvShowRepository {
    private FavoriteTvShowDao favoriteTvShowDao;
    private Cursor favoriteTvShowList;
    private MutableLiveData<Cursor> favoriteTvShowCursor = new MutableLiveData<>();
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public FavoriteTvShowRepository(Application application) {
        FavoriteTvShowDatabase favoriteFilmDatabase = FavoriteTvShowDatabase.getInstance(application);
        mutableIsLoading.setValue(true);
        favoriteTvShowDao = favoriteFilmDatabase.favoriteTvShowDao();
        try {
            favoriteTvShowList = new GetAllFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new FavoriteTvShowRepository.InsertFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new FavoriteTvShowRepository.UpdateFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public void deleteFavoriteTvShow(Long favoriteTvShow) {
        new FavoriteTvShowRepository.DeleteFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public MutableLiveData<Cursor> getAllFavoriteTvShowCursor(){
        favoriteTvShowCursor.setValue(favoriteTvShowList);
        mutableIsLoading.setValue(false);
        return favoriteTvShowCursor;
    }

    public Cursor getFavoriteTvShow(long id) {
        mutableIsLoading.setValue(true);
        try {
            return new FavoriteTvShowRepository.GetFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllFavoriteTvShows() {
        new FavoriteTvShowRepository.DeleteAllFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute();
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

    private static class GetAllFavoriteTvShowsAsyncTask extends AsyncTask<Void, Void, Cursor> {
        private FavoriteTvShowDao favoriteTvShowDao;

        GetAllFavoriteTvShowsAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteTvShowDao.getAllFavoriteTvShows();
        }
    }

    private static class GetFavoriteTvShowsAsyncTask extends AsyncTask<Long, Void, Cursor> {
        private FavoriteTvShowDao favoriteTvShowDao;

        GetFavoriteTvShowsAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Cursor doInBackground(Long... longs) {
            return favoriteTvShowDao.getTvShow(longs[0]);
        }

        @Override
        protected void onPostExecute(Cursor favoriteTvShow) {
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

    private static class DeleteFavoriteTvShowAsyncTask extends AsyncTask<Long, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        DeleteFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(Long... favoriteTvShows) {
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
