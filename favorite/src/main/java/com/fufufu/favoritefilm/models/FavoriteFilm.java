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

@Entity(tableName = "favorite_film_table")
public class FavoriteFilm {
    public static String TABLE_NAME = "favorite_film_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_POSTER_PATH = "posterPath";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_VOTE_AVERAGE = "voteAverage";

    @PrimaryKey
    private long id;
    private String posterPath;
    private String title;
    private Float voteAverage;

    public FavoriteFilm(long id, String posterPath, String title, Float voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    @Ignore
    private FavoriteFilm() {
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

    @BindingAdapter("favoriteFilmPoster")
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
