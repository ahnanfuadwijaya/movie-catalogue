package com.fufufu.moviecatalogue.network;

import com.fufufu.moviecatalogue.BuildConfig;
import com.fufufu.moviecatalogue.models.TvShow;
import com.fufufu.moviecatalogue.models.TvShowDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowDataService {
    String API_KEY = BuildConfig.API_KEY;
    @GET("discover/tv?api_key="+API_KEY)
    Call<TvShowDBResponse> getTvShows(
            @Query("language") String language,
            @Query("sort_by") String sortBy);

    @GET("tv/{tv_id}?api_key="+API_KEY)
    Call<TvShow> getDetailTvShow(
            @Path("tv_id") int tvId,
            @Query("language") String lang);

    @GET("search/tv?api_key="+API_KEY)
    Call<TvShowDBResponse> getTvShowResult(
            @Query("language") String lang,
            @Query("query") String query);
}