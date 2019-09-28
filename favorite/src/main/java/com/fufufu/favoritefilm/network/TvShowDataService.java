package com.fufufu.favoritefilm.network;

import com.fufufu.favoritefilm.BuildConfig;
import com.fufufu.favoritefilm.models.TvShow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowDataService {
    String API_KEY = BuildConfig.API_KEY;
    @GET("tv/{tv_id}?api_key="+API_KEY)
    Call<TvShow> getDetailTvShow(
            @Path("tv_id") long tvId,
            @Query("language") String lang);
}