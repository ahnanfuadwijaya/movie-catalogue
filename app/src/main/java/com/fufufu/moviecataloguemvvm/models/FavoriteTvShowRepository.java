package com.fufufu.moviecataloguemvvm.models;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecataloguemvvm.dao.FavoriteTvShowDao;
import com.fufufu.moviecataloguemvvm.database.FavoriteTvShowDatabase;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteTvShowRepository {
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> favoriteTvShowList;
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public FavoriteTvShowRepository(Application application) {
        FavoriteTvShowDatabase favoriteFilmDatabase = FavoriteTvShowDatabase.getInstance(application);
        mutableIsLoading.setValue(true);
        favoriteTvShowDao = favoriteFilmDatabase.favoriteTvShowDao();
        favoriteTvShowList = favoriteTvShowDao.getAllFavoriteTvShows();
    }

    public void insertFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new FavoriteTvShowRepository.InsertFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public void updateFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new FavoriteTvShowRepository.UpdateFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public void deleteFavoriteTvShow(FavoriteTvShow favoriteTvShow) {
        new FavoriteTvShowRepository.DeleteFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(favoriteTvShow);
    }

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows() {
        mutableIsLoading.setValue(false);
        return favoriteTvShowList;
    }

    public FavoriteTvShow getFavoriteTvShow(int id) {
        mutableIsLoading.setValue(true);
        try {
            return new FavoriteTvShowRepository.GetFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
