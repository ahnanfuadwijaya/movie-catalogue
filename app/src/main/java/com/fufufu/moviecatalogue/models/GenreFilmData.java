package com.fufufu.moviecatalogue.models;

import java.util.ArrayList;

public class GenreFilmData {
    public ArrayList<GenreFilm> data = new ArrayList<>();



    public GenreFilmData(){
        int[] ids = new int[]{28,12,16,35,80,99,18,10751,14,36,27,10402,9648,10749,878,10770,53,10752,37};
        String[] names = new String[]{"Action","Adventure","Animation","Comedy","Crime","Documentary","Drama","Family","Fantasy",
                "History","Horror","Music","Mystery","Romance","Science Fiction","TV Movie","Thriller","War","Western"};

        for(int i = 0; i<ids.length; i++){
           GenreFilm genreFilm = new GenreFilm();
           genreFilm.setId(ids[i]);
           genreFilm.setName(names[i]);
           data.add(genreFilm);
        }
    }
    public ArrayList<GenreFilm> getData(){
        return this.data;
    }
}
