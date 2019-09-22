package com.fufufu.moviecatalogue.network;

import com.fufufu.moviecatalogue.BuildConfig;
import com.fufufu.moviecatalogue.models.Film;
import com.fufufu.moviecatalogue.models.FilmDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmDataService {
    String API_KEY = BuildConfig.API_KEY;
    //https://api.themoviedb.org/3/discover/movie?api_key=f240487696509310687e5998a34a405f&primary_release_date.gte=2019-09-14&primary_release_date.lte=2019-09-14
    @GET("discover/movie?api_key="+API_KEY)
    Call<FilmDBResponse> getFilms(
            @Query("language") String lang,
            @Query("sort_by") String sortBy);

    @GET("discover/movie?api_key="+API_KEY)
    Call<FilmDBResponse> getReleaseFilmToday(
            @Query("language") String lang,
            @Query("primary_release_date.gte") String primaryReleaseDateGte,
            @Query("Primary_release_date.lte") String primaryReleaseDateLte
    );

    @GET("movie/{film_id}?api_key="+API_KEY)
    Call<Film> getDetailFilm(
            @Path("film_id") int filmId,
            @Query("language") String lang);

    @GET("search/movie?api_key="+API_KEY)
    Call<FilmDBResponse> getFilmResult(
            @Query("language") String lang,
            @Query("query") String query);
}
