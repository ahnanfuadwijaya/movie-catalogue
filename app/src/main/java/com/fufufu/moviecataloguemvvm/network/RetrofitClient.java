package com.fufufu.moviecataloguemvvm.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    //https://api.themoviedb.org/3/discover/tv?api_key=f240487696509310687e5998a34a405f&sort_by=popularity.desc&page=1
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static FilmDataService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(FilmDataService.class);
    }
}
