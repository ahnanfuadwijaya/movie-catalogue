package com.fufufu.moviecataloguemvvm.network;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.FilmDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmDataService {
    @GET("discover/movie?api_key=f240487696509310687e5998a34a405f")
    Call<FilmDBResponse> getFilms(
            @Query("language") String lang,
            @Query("sort_by") String sortBy);

    @GET("movie/{film_id}?api_key=f240487696509310687e5998a34a405f")
    Call<Film> getDetailFilm(
            @Path("film_id") int filmId,
            @Query("language") String lang);
}
