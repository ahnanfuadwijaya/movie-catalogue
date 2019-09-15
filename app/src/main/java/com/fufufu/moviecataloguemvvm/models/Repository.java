package com.fufufu.moviecataloguemvvm.models;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecataloguemvvm.network.FilmDataService;
import com.fufufu.moviecataloguemvvm.network.RetrofitClient;
import com.fufufu.moviecataloguemvvm.network.TvShowDataService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ArrayList<Film> films = new ArrayList<>();
    private ArrayList<TvShow> tvShows = new ArrayList<>();
    private MutableLiveData<ArrayList<Film>> mutableFilmLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> mutableTvShowLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private MutableLiveData<Film> detailFilm = new MutableLiveData<>();
    private MutableLiveData<TvShow> detailTvShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Film>> mutableFilmResult = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> mutableTvShowResult  = new MutableLiveData<>();

    public Repository() {
    }

    public MutableLiveData<Boolean> getLoading() {
        return mutableIsLoading;
    }

    public MutableLiveData<ArrayList<Film>> getFilmListFromApi(String lang, String sortBy) {
        FilmDataService userDataService = RetrofitClient.getFilmService();
        Call<FilmDBResponse> call = userDataService.getFilms(lang, sortBy);
        call.enqueue(new Callback<FilmDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilmDBResponse> call, @NonNull Response<FilmDBResponse> response) {
                mutableIsLoading.setValue(true);
                FilmDBResponse filmDBResponse = response.body();
                if (filmDBResponse != null && filmDBResponse.getFilm() != null) {
                    films = filmDBResponse.getFilm();
                    mutableFilmLiveData.setValue(films);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmDBResponse> call, @NonNull Throwable t) {
            }
        });
        mutableIsLoading.setValue(false);
        return mutableFilmLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getFilmResult(String lang, String query) {
        FilmDataService userDataService = RetrofitClient.getFilmService();
        Call<FilmDBResponse> call = userDataService.getFilmResult(lang, query);
        final String querySearch = query;
        call.enqueue(new Callback<FilmDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilmDBResponse> call, @NonNull Response<FilmDBResponse> response) {
                mutableIsLoading.setValue(true);
                FilmDBResponse filmDBResponse = response.body();
                if (filmDBResponse != null && filmDBResponse.getFilm() != null) {
                    films = filmDBResponse.getFilm();
                    mutableFilmResult.setValue(films);
                    String listString = TextUtils.join(", ", films);
                    Log.d("Isi film", listString);
                }
                Log.d("Repository", "Search "+querySearch);
                Log.d("call.enqueue", response.raw().toString());
            }

            @Override
            public void onFailure(@NonNull Call<FilmDBResponse> call, @NonNull Throwable t) {
            }
        });
        mutableIsLoading.setValue(false);
        return mutableFilmResult;
    }

    public MutableLiveData<Film> getDetailFilmFromApi(int filmId, String lang) {
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

    public MutableLiveData<ArrayList<TvShow>> getTvShowListFromApi(String lang, String sortBy) {
        final TvShowDataService userDataService = RetrofitClient.getTvShowService();
        Call<TvShowDBResponse> call = userDataService.getTvShows(lang, sortBy);
        call.enqueue(new Callback<TvShowDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDBResponse> call, @NonNull Response<TvShowDBResponse> response) {
                mutableIsLoading.setValue(true);
                TvShowDBResponse tvShowDBResponse = response.body();
                if (tvShowDBResponse != null && tvShowDBResponse.getTvShow() != null) {
                    tvShows = tvShowDBResponse.getTvShow();
                    mutableTvShowLiveData.setValue(tvShows);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDBResponse> call, @NonNull Throwable t) {
            }

        });
        mutableIsLoading.setValue(false);
        return mutableTvShowLiveData;
    }

    public MutableLiveData<ArrayList<TvShow>> getTvShowResult(String lang, String query) {
        TvShowDataService userDataService = RetrofitClient.getTvShowService();
        Call<TvShowDBResponse> call = userDataService.getTvShowResult(lang, query);
        final String querySearch = query;
        call.enqueue(new Callback<TvShowDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDBResponse> call, @NonNull Response<TvShowDBResponse> response) {
                mutableIsLoading.setValue(true);
                TvShowDBResponse tvShowDBResponse = response.body();
                if (tvShowDBResponse != null && tvShowDBResponse.getTvShow() != null) {
                    tvShows = tvShowDBResponse.getTvShow();
                    mutableTvShowResult.setValue(tvShows);
                    String listString = TextUtils.join(", ", tvShows);
                    Log.d("Isi tv show", listString);
                }
                Log.d("Repository", "Search "+querySearch);
                Log.d("call.enqueue", response.raw().toString());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDBResponse> call, @NonNull Throwable t) {
            }
        });
        mutableIsLoading.setValue(false);
        return mutableTvShowResult;
    }

    public MutableLiveData<TvShow> getDetailTvShowFromApi(int tvId, String lang) {
        TvShowDataService userDataService = RetrofitClient.getTvShowService();
        Call<TvShow> call = userDataService.getDetailTvShow(tvId, lang);
        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                mutableIsLoading.setValue(true);
                if (response.body() != null) {
                    detailTvShow.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {

            }
        });
        mutableIsLoading.setValue(false);
        return detailTvShow;
    }
}