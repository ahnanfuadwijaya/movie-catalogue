package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.models.TvShowDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowDataService {
    @GET("discover/tv?api_key=f240487696509310687e5998a34a405f")
    Call<TvShowDBResponse> getTvShows(
            @Query("language") String language,
            @Query("sort_by") String sortBy);

    @GET("tv/{tv_id}?api_key=f240487696509310687e5998a34a405f")
    Call<TvShow> getDetailTvShow(
            @Path("tv_id") int tvId,
            @Query("language") String lang);
}