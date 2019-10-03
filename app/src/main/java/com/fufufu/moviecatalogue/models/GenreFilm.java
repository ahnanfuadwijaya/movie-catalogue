package com.fufufu.moviecatalogue.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GenreFilm implements Parcelable {
    private long id;
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
