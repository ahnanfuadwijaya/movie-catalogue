package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.models.TvShowDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowDataService {
    @GET("discover/tv")
    Call<TvShowDBResponse> getTvShows(
            @Query("api_key")String apiKey,
            @Query("language")String language,
            @Query("sort_by")String sortBy);
}