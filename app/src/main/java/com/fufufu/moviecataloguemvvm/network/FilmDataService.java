package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.BuildConfig;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.FilmDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmDataService {
    String API_KEY = BuildConfig.API_KEY;
    @GET("discover/movie?api_key="+API_KEY)
    Call<FilmDBResponse> getFilms(
            @Query("language") String lang,
            @Query("sort_by") String sortBy);

    @GET("movie/{film_id}?api_key="+API_KEY)
    Call<Film> getDetailFilm(
            @Path("film_id") int filmId,
            @Query("language") String lang);

    @GET("search/movie?api_key="+API_KEY)
    Call<FilmDBResponse> getFilmResult(
            @Query("language") String lang,
            @Query("query") String query);
}
