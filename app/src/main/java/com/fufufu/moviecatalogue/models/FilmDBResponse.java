package com.fufufu.moviecatalogue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class FilmDBResponse {
    @SerializedName("results")
    @Expose
    private ArrayList<Film> filmList;


    public ArrayList<Film> getFilm() {
        return filmList;
    }

    public void setFilm(ArrayList<Film> filmList) {
        this.filmList = filmList;
    }
}
