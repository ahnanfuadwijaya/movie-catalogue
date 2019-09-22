package com.fufufu.favoritefilm.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.fufufu.favoritefilm.dao.FavoriteFilmDao;
import com.fufufu.favoritefilm.database.FavoriteFilmDatabase;
import com.fufufu.favoritefilm.models.FavoriteFilm;

public class FavoriteFilmContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.fufufu.favoritefilm.providers";
    private static final int FAVORITE_FILM = 1;
    private static final int FAVORITE_FILM_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, FavoriteFilm.TABLE_NAME, FAVORITE_FILM);
        uriMatcher.addURI(AUTHORITY, FavoriteFilm.TABLE_NAME + "/*", FAVORITE_FILM_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final int code = uriMatcher.match(uri);
        if (code == FAVORITE_FILM || code == FAVORITE_FILM_ID) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            FavoriteFilmDao favoriteFilmDao = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao();
            final Cursor cursor;
            if (code == FAVORITE_FILM) {
                cursor = favoriteFilmDao.getAllFavoriteFilms();
            } else {
                cursor = favoriteFilmDao.getFilm(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_FILM:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + FavoriteFilm.TABLE_NAME;
            case FAVORITE_FILM_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + FavoriteFilm.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_FILM:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao()
                        .insertFavoriteFilm(FavoriteFilm.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case FAVORITE_FILM_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
