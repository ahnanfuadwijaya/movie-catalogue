package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.models.TvShowDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowDataService {
    @GET("discover/tv")
    Call<TvShowDBResponse> getTvShows(
            @Query("api_key")String apiKey,
            @Query("language")String language,
            @Query("sort_by")String sortBy);
    @GET("tv/{tv_id}")
    Call<TvShow> getDetailTvShow(
            @Path("tv_id")int tvId,
            @Query("api_key")String apiKey,
            @Query("language")String lang);
}