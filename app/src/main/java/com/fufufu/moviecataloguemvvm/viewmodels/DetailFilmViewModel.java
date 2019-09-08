package com.fufufu.moviecataloguemvvm.viewmodels;

import androidx.lifecycle.ViewModel;

import com.fufufu.moviecataloguemvvm.models.Film;

public class DetailFilmViewModel extends ViewModel {
    private Film film;

    public DetailFilmViewModel(){
        super();
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
