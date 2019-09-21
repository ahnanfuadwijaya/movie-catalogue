package com.fufufu.favoritefilm.models;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fufufu.favoritefilm.R;

import java.text.DecimalFormat;

@Entity(tableName = "favorite_film_table")
public class FavoriteFilm {
    @PrimaryKey
    private int id;
    private String posterPath;
    private String title;
    private Float voteAverage;

    public FavoriteFilm(int id, String posterPath, String title, Float voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    @Ignore
    public FavoriteFilm() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    @BindingAdapter("voteFilmAverageValue")
    public static void voteFilmAverageValue(TextView textView, Float voteAverage) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        textView.setText(decimalFormat.format(voteAverage));
    }

    @BindingAdapter("filmPoster")
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().centerInside())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}