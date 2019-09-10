package com.fufufu.moviecataloguemvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailFilmDBResponse {
    @Expose
    private Film film;
        public Film getFilm() {
            return film;
        }

        public void setFilm(Film film) {
            this.film = film;
        }
}
