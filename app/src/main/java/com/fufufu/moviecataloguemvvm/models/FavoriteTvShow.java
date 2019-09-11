package com.fufufu.moviecataloguemvvm.models;

import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.text.DecimalFormat;

@Entity(tableName = "favorite_tv_show_table")
public class FavoriteTvShow {
    @PrimaryKey
    private int id;
    private String posterPath;
    private String name;
    private Float voteAverage;

    public FavoriteTvShow(int id, String posterPath, String name, Float voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.name = name;
        this.voteAverage = voteAverage;
    }

    @Ignore
    public FavoriteTvShow() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getName() {
        return name;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    @BindingAdapter({"voteTvShowAverageValue"})
    public static void voteTvShowAverageValue(TextView textView, Float voteAverage) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        textView.setText(decimalFormat.format(voteAverage));
    }
}
