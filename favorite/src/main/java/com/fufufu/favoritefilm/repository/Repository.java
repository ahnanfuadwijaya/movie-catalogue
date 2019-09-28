package com.fufufu.favoritefilm.repository;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.models.FavoriteTvShow;
import com.fufufu.favoritefilm.models.Film;
import com.fufufu.favoritefilm.models.TvShow;
import com.fufufu.favoritefilm.network.FilmDataService;
import com.fufufu.favoritefilm.network.RetrofitClient;
import com.fufufu.favoritefilm.network.TvShowDataService;
import com.fufufu.favoritefilm.views.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private Cursor favoriteFilmsCursor;
    private Cursor favoriteTvShowsCursor;
    private MutableLiveData<Film> detailFilm = new MutableLiveData<>();
    private MutableLiveData<TvShow> detailTvShow = new MutableLiveData<>();

    private String[] projection;
    private String selection = null;
    private String[] selectionArguments = null;
    private String sortOrder = null;
    private final String SCHEME = "content";
    private String AUTHORITY;
    private final String FAVORITE_FILM_TABLE_NAME = FavoriteFilm.TABLE_NAME;
    private final String FAVORITE_TV_SHOW_TABLE_NAME = FavoriteTvShow.TABLE_NAME;
    private String URL;
    private Uri uri;
    private String myMimeType;
    private ContentResolver contentResolver;

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public Repository() {
        mutableIsLoading.setValue(null);
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
        AUTHORITY = "com.fufufu.moviecatalogue.favoritefilm";
        projection = new String[]{"id", "title", "voteAverage", "posterPath"};
        selection = null;
        selectionArguments = null;
        sortOrder = null;
        uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAVORITE_FILM_TABLE_NAME)
                .build();
        Context applicationContext = MainActivity.getContextOfApplication();
        contentResolver = applicationContext.getContentResolver();
        myMimeType = contentResolver.getType(uri);

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
                .appendPath(FAVORITE_FILM_TABLE_NAME)
                .appendPath(String.valueOf(id))
                .build();
        selectionArguments = new String[]{String.valueOf(id)};
        contentResolver.delete(uri, "_ID=?", selectionArguments);

    }

    public MutableLiveData<TvShow> getDetailTvShowFromApi(final long tvShowId, final String lang) {
        TvShowDataService userDataService = RetrofitClient.getTvShowService();
        Call<TvShow> call = userDataService.getDetailTvShow(tvShowId, lang);
        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                if (response.body() != null) {
                    detailTvShow.setValue(response.body());
                    mutableIsLoading.setValue(true);
                }
            }
            @Override
            public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
            }
        });
        return detailTvShow;
    }

    public Cursor getAllFavoriteTvShows() {
        AUTHORITY = "com.fufufu.moviecatalogue.favoritetvshow";
        projection = new String[]{"id", "name", "voteAverage", "posterPath"};
        selection = null;
        selectionArguments = null;
        sortOrder = null;
        uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAVORITE_TV_SHOW_TABLE_NAME)
                .build();
        Context applicationContext = MainActivity.getContextOfApplication();
        contentResolver = applicationContext.getContentResolver();
        myMimeType = contentResolver.getType(uri);

        Log.d("getAllFavoriteTvShowR", "executed");
        Log.d("mimeTypeCR", myMimeType != null ? myMimeType : "null");
        favoriteTvShowsCursor = contentResolver.query(uri, projection, selection, selectionArguments, sortOrder);
        mutableIsLoading.setValue(true);
        Log.d("Loading Fav Fil repo", String.valueOf(mutableIsLoading.getValue()));
        if(favoriteTvShowsCursor != null){
            return favoriteTvShowsCursor;
        }
        Log.d("Return", "null");
        return null;
    }

    public void deleteFavoriteTvShow(long id){
        uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAVORITE_TV_SHOW_TABLE_NAME)
                .appendPath(String.valueOf(id))
                .build();
        selectionArguments = new String[]{String.valueOf(id)};
        contentResolver.delete(uri, "_ID=?", selectionArguments);
    }
}