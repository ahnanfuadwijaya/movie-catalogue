package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.models.FilmDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmDataService {
        @GET("discover/movie?api_key=f240487696509310687e5998a34a405f&sort_by=popularity.desc")
        Call<FilmDBResponse> getFilms();
}
