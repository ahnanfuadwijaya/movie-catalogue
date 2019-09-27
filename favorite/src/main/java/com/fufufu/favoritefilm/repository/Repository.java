package com.fufufu.favoritefilm.repository;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.fufufu.favoritefilm.models.Film;
import com.fufufu.favoritefilm.network.FilmDataService;
import com.fufufu.favoritefilm.network.RetrofitClient;
import com.fufufu.favoritefilm.views.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.BaseColumns._ID;

public class Repository {
    private Context context;
    private FavoriteFilmDao favoriteFilmDao;
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> favoriteTvShowList;
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FavoriteFilm>> favoriteFilmList = new MutableLiveData<>();
    private Cursor favoriteFilmsCursor;
    private MutableLiveData<Film> detailFilm = new MutableLiveData<>();


    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public Repository(Application application) {
        FavoriteFilmDatabase favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(application);
        this.context = application.getApplicationContext();
        favoriteFilmDao = favoriteFilmDatabase.favoriteFilmDao();
        FavoriteTvShowDatabase favoriteTvShowDatabase = FavoriteTvShowDatabase.getInstance(application);
        favoriteTvShowDao = favoriteTvShowDatabase.favoriteTvShowDao();
        favoriteTvShowList = favoriteTvShowDao.getAllFavoriteTvShows();
        favoriteFilmsCursor = null;
        mutableIsLoading.setValue(true);
        FavoriteFilm favoriteFilm = new FavoriteFilm();
        favoriteFilm.setId(605934);
        favoriteFilm.setVoteAverage(19.851f);
        favoriteFilm.setPosterPath("/2NQ4JkTj0vCGbHpTVW0Pkau6xhF.jpg");
        favoriteFilm.setTitle("I Remember Ashes");
        insertFavoriteFilm(favoriteFilm);
    }

    public MutableLiveData<Film> getDetailFilmFromApi(long filmId, String lang) {
        FilmDataService userDataService = RetrofitClient.getFilmService();
        Call<Film> call = userDataService.getDetailFilm(filmId, lang);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(@NonNull Call<Film> call, @NonNull Response<Film> response) {
                mutableIsLoading.setValue(true);
                if (response.body() != null) {
                    detailFilm.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Film> call, @NonNull Throwable t) {

            }
        });
        mutableIsLoading.setValue(false);
        return detailFilm;
    }

    public void insertFavoriteFilm(FavoriteFilm favoriteFilm) {
        new InsertFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void updateFavoriteFilm(FavoriteFilm favoriteFilm) {
        new UpdateFavoriteFilmAsyncTask(favoriteFilmDao).execute(favoriteFilm);
    }

    public void deleteFavoriteFilm(Long id) {
        new DeleteFavoriteFilmAsyncTask(favoriteFilmDao).execute(id);
    }

    public Cursor getAllFavoriteFilms() {
        Log.d("getAllFavorite Repo", "executed");
        mutableIsLoading.setValue(true);
        Context applicationContext = MainActivity.getContextOfApplication();
        ContentResolver contentResolver = applicationContext.getContentResolver();
        String[] projection = new String[]{"id", "title", "voteAverage", "posterPath"};
        String selection = null;
        String[] selectionArguments = null;
        String sortOrder = null;
        String AUTHORITY = "com.fufufu.moviecatalogue";
        String URL = "content://" + AUTHORITY + "/" + FavoriteFilm.TABLE_NAME;
        Uri uri = Uri.parse(URL);
        String myMimeType = contentResolver.getType(uri);
        Log.d("mimeTypeCR", myMimeType != null ? myMimeType : "null");
        favoriteFilmsCursor = contentResolver.query(uri, projection, selection, selectionArguments, sortOrder);
        mutableIsLoading.setValue(false);
        if(favoriteFilmsCursor != null){
            return favoriteFilmsCursor;
        }
        Log.d("Return", "null");
        return null;
    }

    public Cursor getFavoriteFilm(long id) {
        mutableIsLoading.setValue(true);
        try {
            return new GetFavoriteFilmAsyncTask(favoriteFilmDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
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

    private static class GetAllFavoriteFilmsAsyncTask extends AsyncTask<Void, Void, Cursor> {
        private FavoriteFilmDao favoriteFilmDao;

        GetAllFavoriteFilmsAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteFilmDao.getAllFavoriteFilms();
        }
    }

    private static class GetFavoriteFilmAsyncTask extends AsyncTask<Long, Void, Cursor> {
        private FavoriteFilmDao favoriteFilmDao;

        GetFavoriteFilmAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Cursor doInBackground(Long... longs) {
            return favoriteFilmDao.getFilm(longs[0]);
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