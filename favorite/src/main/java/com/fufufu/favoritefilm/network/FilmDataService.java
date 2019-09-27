package com.fufufu.favoritefilm.network;
import com.fufufu.favoritefilm.BuildConfig;
import com.fufufu.favoritefilm.models.Film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmDataService {
    String API_KEY = BuildConfig.API_KEY;

    @GET("movie/{film_id}?api_key="+API_KEY)
    Call<Film> getDetailFilm(
            @Path("film_id") long filmId,
            @Query("language") String lang);
}
