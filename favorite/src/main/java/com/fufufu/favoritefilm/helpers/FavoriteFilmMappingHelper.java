package com.fufufu.favoritefilm.helpers;

import android.database.Cursor;

import com.fufufu.favoritefilm.models.FavoriteFilm;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class FavoriteFilmMappingHelper {
    public static ArrayList<FavoriteFilm> mapCursorToArrayList(Cursor cursor){
        ArrayList<FavoriteFilm> favoriteFilms = new ArrayList<>();
        while (cursor.moveToFirst()){
            long id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
            Float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("voteAverage"));
            favoriteFilms.add(new FavoriteFilm(id, posterPath, title, voteAverage));
        }
        return favoriteFilms;
    }
}
