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

public class Repository {
    private FavoriteFilmDao favoriteFilmDao;
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> favoriteTvShowList;
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FavoriteFilm>> favoriteFilmList = new MutableLiveData<>();
    private Cursor favoriteFilmsCursor;
    private MutableLiveData<Film> detailFilm = new MutableLiveData<>();

    private String[] projection;
    private String selection = null;
    private String[] selectionArguments = null;
    private String sortOrder = null;
    private final String SCHEME = "content";
    private final String AUTHORITY = "com.fufufu.moviecatalogue";
    private final String TABLE_NAME = FavoriteFilm.TABLE_NAME;
    private String URL;
    private Uri uri;
    private String myMimeType;

    private ContentResolver contentResolver;

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public Repository(Application application) {
        FavoriteFilmDatabase favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(application);
        mutableIsLoading.setValue(null);

        projection = new String[]{"id", "title", "voteAverage", "posterPath"};
        selection = null;
        selectionArguments = null;
        sortOrder = null;
        uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
        Context applicationContext = MainActivity.getContextOfApplication();
        contentResolver = applicationContext.getContentResolver();
        myMimeType = contentResolver.getType(uri);

        favoriteFilmDao = favoriteFilmDatabase.favoriteFilmDao();
        FavoriteTvShowDatabase favoriteTvShowDatabase = FavoriteTvShowDatabase.getInstance(application);
        favoriteTvShowDao = favoriteTvShowDatabase.favoriteTvShowDao();
        favoriteTvShowList = favoriteTvShowDao.getAllFavoriteTvShows();
        favoriteFilmsCursor = null;
    }

    public MutableLiveData<Film> getDetailFilmFromApi(final long filmId, final String lang) {
        FilmDataService userDataService = RetrofitClient.getFilmService();
        Call<Film> call = userDataService.getDetailFilm(filmId, lang);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(@NonNull Call<Film> call, @NonNull Response<Film> response) {
                if (response.body() != null) {
                    detailFilm.setValue(response.body());
                    mutableIsLoading.setValue(true);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Film> call, @NonNull Throwable t) {
            }
        });
        return detailFilm;
    }

    public Cursor getAllFavoriteFilms() {
        Log.d("getAllFavorite Repo", "executed");
        Log.d("mimeTypeCR", myMimeType != null ? myMimeType : "null");
        favoriteFilmsCursor = contentResolver.query(uri, projection, selection, selectionArguments, sortOrder);
        mutableIsLoading.setValue(true);
        Log.d("Loading Fav Fil repo", String.valueOf(mutableIsLoading.getValue()));
        if(favoriteFilmsCursor != null){
            return favoriteFilmsCursor;
        }
        Log.d("Return", "null");
        return null;
    }

    public void deleteFavoriteFilm(long id){
        uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .appendPath(String.valueOf(id))
                .build();
        selectionArguments = new String[]{String.valueOf(id)};
        contentResolver.delete(uri, "_ID=?", selectionArguments);

    }
}