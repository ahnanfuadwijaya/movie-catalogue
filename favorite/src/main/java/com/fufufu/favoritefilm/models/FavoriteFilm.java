package com.fufufu.favoritefilm.models;

import android.content.ContentValues;
import android.provider.BaseColumns;
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
    public static String TABLE_NAME = "favorite_film_table";
    public static final String COLUMN_ID = BaseColumns._ID;
    @PrimaryKey
    private long id;
    public static final String COLUMN_POSTER_PATH = "posterPath";
    private String posterPath;
    public static final String COLUMN_TITLE = "title";
    private String title;
    public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
    private Float voteAverage;

    public FavoriteFilm(long id, String posterPath, String title, Float voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    @Ignore
    public FavoriteFilm() {
    }

    public void setId(long id) {
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

    public long getId() {
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

    public static FavoriteFilm fromContentValues(ContentValues values) {
        final FavoriteFilm favoriteFilm = new FavoriteFilm();
        if (values.containsKey(COLUMN_ID)) {
            favoriteFilm.setId(values.getAsLong(COLUMN_ID));
        }
        if (values.containsKey(COLUMN_POSTER_PATH)) {
            favoriteFilm.setPosterPath(values.getAsString(COLUMN_POSTER_PATH));
        }
        if (values.containsKey(COLUMN_TITLE)) {
            favoriteFilm.setTitle(values.getAsString(COLUMN_TITLE));
        }
        if (values.containsKey(COLUMN_VOTE_AVERAGE)) {
            favoriteFilm.setVoteAverage(values.getAsFloat(COLUMN_VOTE_AVERAGE));
        }
        return favoriteFilm;
    }
}
