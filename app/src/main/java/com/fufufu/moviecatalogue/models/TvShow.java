package com.fufufu.moviecatalogue.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fufufu.moviecatalogue.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {
    private final String posterBaseUrl = "https://image.tmdb.org/t/p/w500";
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getVoteAverage() {
        return voteAverage.toString();
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterBaseUrl + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Creator<TvShow> getCREATOR() {
        return CREATOR;
    }

    protected TvShow(Parcel in) {
        id = in.readLong();
        posterPath = in.readString();
        overview = in.readString();
        firstAirDate = in.readString();
        name = in.readString();
        originalName = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readFloat();
        }
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(firstAirDate);
        parcel.writeString(name);
        parcel.writeString(originalName);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(voteAverage);
        }
    }

    @BindingAdapter({"tvShowPoster"})
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().centerInside())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}