package com.fufufu.favoritefilm.models;

import android.content.ContentValues;
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

@Entity(tableName = "favorite_tv_show_table")
public class FavoriteTvShow {
    public static String TABLE_NAME = "favorite_tv_show_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POSTER_PATH = "posterPath";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VOTE_AVERAGE = "voteAverage";

    @PrimaryKey
    private long id;
    private String posterPath;
    private String name;
    private Float voteAverage;

    public FavoriteTvShow(long id, String posterPath, String name, Float voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.name = name;
        this.voteAverage = voteAverage;
    }

    @Ignore
    public FavoriteTvShow() {
    }

    public void setId(long id) {
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

    public long getId() {
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

    @BindingAdapter("voteTvShowAverageValue")
    public static void voteTvShowAverageValue(TextView textView, Float voteAverage) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        textView.setText(decimalFormat.format(voteAverage));
    }

    @BindingAdapter("favoriteTvShowPoster")
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().centerInside())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }

    public static FavoriteTvShow fromContentValues(ContentValues values) {
        final FavoriteTvShow favoriteTvShow = new FavoriteTvShow();
        if (values.containsKey(COLUMN_ID)) {
            favoriteTvShow.setId(values.getAsLong(COLUMN_ID));
        }
        if (values.containsKey(COLUMN_POSTER_PATH)) {
            favoriteTvShow.setPosterPath(values.getAsString(COLUMN_POSTER_PATH));
        }
        if (values.containsKey(COLUMN_NAME)) {
            favoriteTvShow.setName(values.getAsString(COLUMN_NAME));
        }
        if (values.containsKey(COLUMN_VOTE_AVERAGE)) {
            favoriteTvShow.setVoteAverage(values.getAsFloat(COLUMN_VOTE_AVERAGE));
        }
        return favoriteTvShow;
    }
}
