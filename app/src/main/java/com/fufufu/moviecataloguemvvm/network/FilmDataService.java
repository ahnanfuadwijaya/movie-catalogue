package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.models.FilmDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmDataService {
        @GET("discover/movie")
        Call<FilmDBResponse> getFilms(
                @Query("api_key")String apiKey,
                @Query("language")String lang,
                @Query("sort_by")String sortBy);
}
