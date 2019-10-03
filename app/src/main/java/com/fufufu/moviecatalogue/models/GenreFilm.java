package com.fufufu.moviecatalogue.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "genre_film_table")
public class GenreFilm implements Parcelable {
    public static final String tableName = "genre_film_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;

    public GenreFilm(){

    }

    private GenreFilm(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<GenreFilm> CREATOR = new Creator<GenreFilm>() {
        @Override
        public GenreFilm createFromParcel(Parcel in) {
            return new GenreFilm(in);
        }

        @Override
        public GenreFilm[] newArray(int size) {
            return new GenreFilm[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
    }
}
