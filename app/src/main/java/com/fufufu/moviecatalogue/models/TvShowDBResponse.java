package com.fufufu.moviecatalogue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TvShowDBResponse {
    @SerializedName("results")
    @Expose
    private ArrayList<TvShow> tvShowList = null;

    public ArrayList<TvShow> getTvShow() {
        return tvShowList;
    }

    public void setTvShow(ArrayList<TvShow> tvShowList) {
        this.tvShowList = tvShowList;
    }
}
