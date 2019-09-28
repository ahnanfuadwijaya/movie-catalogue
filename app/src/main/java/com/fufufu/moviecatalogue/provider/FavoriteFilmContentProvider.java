package com.fufufu.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.fufufu.moviecatalogue.dao.FavoriteFilmDao;
import com.fufufu.moviecatalogue.database.FavoriteFilmDatabase;
import com.fufufu.moviecatalogue.models.FavoriteFilm;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FavoriteFilmContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.fufufu.moviecatalogue.favoritefilm";
    public static final String URL = "content://" + AUTHORITY + "/" + FavoriteFilm.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);
    private static final int FAVORITE_FILMS = 1;
    private static final int FAVORITE_FILM_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, FavoriteFilm.TABLE_NAME, FAVORITE_FILMS);
        uriMatcher.addURI(AUTHORITY, FavoriteFilm.TABLE_NAME + "/*", FAVORITE_FILM_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final int code = uriMatcher.match(uri);
        if (code == FAVORITE_FILMS || code == FAVORITE_FILM_ID) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            FavoriteFilmDao favoriteFilmDao = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao();
            final Cursor cursor;
            if (code == FAVORITE_FILMS) {
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
            case FAVORITE_FILMS:
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
            case FAVORITE_FILMS:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                FavoriteFilmDao favoriteFilmDao = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao();
                final long id = favoriteFilmDao.insertFavoriteFilm(FavoriteFilm.fromContentValues(contentValues));
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
        switch (uriMatcher.match(uri)) {
            case FAVORITE_FILMS:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case FAVORITE_FILM_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                FavoriteFilmDao favoriteFilmDao = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao();
                final int id = favoriteFilmDao.deleteFavoriteFilm(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return id;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_FILMS:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case FAVORITE_FILM_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                FavoriteFilmDao favoriteFilmDao = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao();
                final int id = favoriteFilmDao.updateFavoriteFilm(FavoriteFilm.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return id;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull final ArrayList<ContentProviderOperation> operations) {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final FavoriteFilmDatabase database = FavoriteFilmDatabase.getInstance(context);
        return database.runInTransaction(new Callable<ContentProviderResult[]>() {
            @Override
            public ContentProviderResult[] call() throws OperationApplicationException {
                return FavoriteFilmContentProvider.super.applyBatch(operations);
            }
        });
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_FILMS:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final FavoriteFilmDatabase database = FavoriteFilmDatabase.getInstance(context);
                final FavoriteFilm[] favoriteFilms = new FavoriteFilm[values.length];
                for (int i = 0; i < values.length; i++) {
                    favoriteFilms[i] = FavoriteFilm.fromContentValues(values[i]);
                }
                return database.favoriteFilmDao().insertAllFavoriteFilms(favoriteFilms).length;
            case FAVORITE_FILM_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
