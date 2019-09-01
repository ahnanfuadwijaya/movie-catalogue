package com.fufufu.moviecataloguemvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TvShowDBResponse {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private ArrayList<TvShow> tvShowList = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<TvShow> getTvShow() {
        return tvShowList;
    }

    public void setTvShow(ArrayList<TvShow> tvShowList) {
        this.tvShowList = tvShowList;
    }
}
