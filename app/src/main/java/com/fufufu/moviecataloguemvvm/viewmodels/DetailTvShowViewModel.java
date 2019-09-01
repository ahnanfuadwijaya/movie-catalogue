package com.fufufu.moviecataloguemvvm.viewmodels;

import androidx.lifecycle.ViewModel;
import com.fufufu.moviecataloguemvvm.models.TvShow;

public class DetailTvShowViewModel extends ViewModel {
    private TvShow tvShow;

    public DetailTvShowViewModel(){
        super();
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }
}
