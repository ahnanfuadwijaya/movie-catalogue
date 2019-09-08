package com.fufufu.moviecataloguemvvm.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fufufu.moviecataloguemvvm.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Film implements Parcelable {
    private final String posterBaseUrl = "https://image.tmdb.org/t/p/w500/";

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterBaseUrl+posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteAverage() {
        return voteAverage.toString();
    }
    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public static Creator<Film> getCREATOR() {
        return CREATOR;
    }

    public Film(){

    }

    protected Film(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        originalTitle = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readFloat();
        }
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(voteAverage);
        }
    }
    @BindingAdapter({ "filmPoster" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().centerInside())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}
