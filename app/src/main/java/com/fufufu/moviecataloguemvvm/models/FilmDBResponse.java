package com.fufufu.moviecataloguemvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class FilmDBResponse {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private ArrayList<Film> filmList;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<Film> getFilm() {
        return filmList;
    }

    public void setFilm(ArrayList<Film> filmList) {
        this.filmList = filmList;
    }
}
