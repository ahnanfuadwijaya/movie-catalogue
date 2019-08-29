package com.fufufu.moviecataloguemvvm.models;

import android.util.Log;

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

    public Repository(){

    }

    public MutableLiveData<ArrayList<Film>> getFilmListFromApi(){
        final FilmDataService userDataService = RetrofitClient.getFilmService();

        Call<FilmDBResponse> call = userDataService.getFilms();
        call.enqueue(new Callback<FilmDBResponse>() {
            @Override
            public void onResponse(Call<FilmDBResponse> call, Response<FilmDBResponse> response) {
                FilmDBResponse filmDBResponse = response.body();
                if (filmDBResponse != null && filmDBResponse.getFilm() != null) {
                    films = filmDBResponse.getFilm();


                    //debug
                    StringBuilder sb = new StringBuilder();
                    for (Film film : films){
                        sb.append(film.getPosterPath());
                        sb.append(";");
                    }
                    String sSb = sb.toString();
                    Log.d("Film ID", sSb);
                    mutableFilmLiveData.setValue(films);
                }
            }

            @Override
            public void onFailure(Call<FilmDBResponse> call, Throwable t) {
                Log.d("call.enqueue", "Failed");
            }
        });
        Log.d("getFromAPI", "Tes");
        return mutableFilmLiveData;
    }

    public MutableLiveData<ArrayList<TvShow>> getTvShowListFromApi(){
        final TvShowDataService userDataService = RetrofitClient.getTvShowService();

        Call<TvShowDBResponse> call = userDataService.getTvShows();
        call.enqueue(new Callback<TvShowDBResponse>() {
            @Override
            public void onResponse(Call<TvShowDBResponse> call, Response<TvShowDBResponse> response) {
                TvShowDBResponse tvShowDBResponse = response.body();
                if (tvShowDBResponse != null && tvShowDBResponse.getTvShow() != null) {
                    tvShows = tvShowDBResponse.getTvShow();


                    //debug
                    StringBuilder sb = new StringBuilder();
                    for (TvShow tvShow : tvShows){
                        sb.append(tvShow.getPosterPath());
                        sb.append(";");
                    }
                    String sSb = sb.toString();
                    Log.d("Film ID", sSb);
                    mutableTvShowLiveData.setValue(tvShows);
                }
            }

            @Override
            public void onFailure(Call<TvShowDBResponse> call, Throwable t) {
                Log.d("call.enqueue", "Failed");
            }
        });
        Log.d("getFromAPI", "Tes");
        return mutableTvShowLiveData;
    }
}
