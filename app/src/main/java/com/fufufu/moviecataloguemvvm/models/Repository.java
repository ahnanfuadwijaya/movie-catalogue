package com.fufufu.moviecataloguemvvm.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.moviecataloguemvvm.network.FilmDataService;
import com.fufufu.moviecataloguemvvm.network.RetrofitClient;
import com.fufufu.moviecataloguemvvm.network.TvShowDataService;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ArrayList<Film> films = new ArrayList<>();
    private ArrayList<TvShow> tvShows = new ArrayList<>();
    private MutableLiveData<ArrayList<Film>> mutableFilmLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> mutableTvShowLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableIsLoading=new MutableLiveData<>();

    public Repository(){
    }

    public MutableLiveData<Boolean> getLoading(){
        return mutableIsLoading;
    }

    public MutableLiveData<ArrayList<Film>> getFilmListFromApi(String apiKey, String lang, String sortBy){
        FilmDataService userDataService = RetrofitClient.getFilmService();
        Call<FilmDBResponse> call = userDataService.getFilms(apiKey, lang, sortBy);
        call.enqueue(new Callback<FilmDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilmDBResponse> call, @NonNull Response<FilmDBResponse> response) {
                FilmDBResponse filmDBResponse = response.body();
                mutableIsLoading.setValue(true);
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

    public MutableLiveData<ArrayList<TvShow>> getTvShowListFromApi(String apiKey, String lang, String sortBy){
        final TvShowDataService userDataService = RetrofitClient.getTvShowService();
        Call<TvShowDBResponse> call = userDataService.getTvShows(apiKey, lang, sortBy);
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
}