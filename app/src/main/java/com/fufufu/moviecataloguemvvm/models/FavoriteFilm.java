package com.fufufu.moviecataloguemvvm.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_film_table")
public class FavoriteFilm {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String title;
    private String originalTitle;
    private Float voteAverage;

    public FavoriteFilm(String posterPath, String overview, String releaseDate, String title, String originalTitle, Float voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }
}
