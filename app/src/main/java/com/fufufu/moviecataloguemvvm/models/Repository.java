package com.fufufu.moviecataloguemvvm.models;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.FilmDBResponse;
import com.fufufu.moviecataloguemvvm.network.FilmDataService;
import com.fufufu.moviecataloguemvvm.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ArrayList<Film> films = new ArrayList<>();
    private MutableLiveData<ArrayList<Film>> mutableLiveData = new MutableLiveData<>();

    public Repository(){

    }

    public MutableLiveData<ArrayList<Film>> getFilmListFromApi(){
        final FilmDataService userDataService = RetrofitClient.getService();

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
                    mutableLiveData.setValue(films);
                }
            }

            @Override
            public void onFailure(Call<FilmDBResponse> call, Throwable t) {
                Log.d("call.enqueue", "Failed");
            }
        });
        Log.d("getFromAPI", "Tes");
        return mutableLiveData;
    }
}
