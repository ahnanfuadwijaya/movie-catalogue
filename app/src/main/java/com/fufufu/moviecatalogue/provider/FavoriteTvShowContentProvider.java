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
import com.fufufu.moviecatalogue.dao.FavoriteTvShowDao;
import com.fufufu.moviecatalogue.database.FavoriteFilmDatabase;
import com.fufufu.moviecatalogue.database.FavoriteTvShowDatabase;
import com.fufufu.moviecatalogue.models.FavoriteFilm;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FavoriteTvShowContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.fufufu.moviecatalogue.favoritetvshow";
    public static final String URL = "content://" + AUTHORITY + "/" + FavoriteTvShow.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);
    private static final int FAVORITE_TV_SHOW = 1;
    private static final int FAVORITE_TV_SHOW_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, FavoriteTvShow.TABLE_NAME, FAVORITE_TV_SHOW);
        uriMatcher.addURI(AUTHORITY, FavoriteTvShow.TABLE_NAME + "/*", FAVORITE_TV_SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final int code = uriMatcher.match(uri);
        if (code == FAVORITE_TV_SHOW || code == FAVORITE_TV_SHOW_ID) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            FavoriteTvShowDao favoriteTvShowDao = FavoriteTvShowDatabase.getInstance(context).favoriteTvShowDao();
            final Cursor cursor;
            if (code == FAVORITE_TV_SHOW) {
                cursor = favoriteTvShowDao.getAllFavoriteTvShows();
            } else {
                cursor = favoriteTvShowDao.getTvShow(ContentUris.parseId(uri));
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
            case FAVORITE_TV_SHOW:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + FavoriteTvShow.TABLE_NAME;
            case FAVORITE_TV_SHOW_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + FavoriteTvShow.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_TV_SHOW:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                FavoriteTvShowDao favoriteTvShowDao = FavoriteTvShowDatabase.getInstance(context).favoriteTvShowDao();
                final long id = favoriteTvShowDao.insertFavoriteTvShow(FavoriteTvShow.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case FAVORITE_TV_SHOW_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_TV_SHOW:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID " + uri);
            case FAVORITE_TV_SHOW_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                FavoriteTvShowDao favoriteTvShowDao = FavoriteTvShowDatabase.getInstance(context).favoriteTvShowDao();
                final int id = favoriteTvShowDao.deleteFavoriteTvShow(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return id;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_TV_SHOW:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case FAVORITE_TV_SHOW_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                FavoriteTvShowDao favoriteTvShowDao = FavoriteTvShowDatabase.getInstance(context).favoriteTvShowDao();
                final int id = favoriteTvShowDao.updateFavoriteTvShow(FavoriteTvShow.fromContentValues(contentValues));
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
        final FavoriteTvShowDatabase database = FavoriteTvShowDatabase.getInstance(context);
        return database.runInTransaction(new Callable<ContentProviderResult[]>() {
            @Override
            public ContentProviderResult[] call() throws OperationApplicationException {
                return FavoriteTvShowContentProvider.super.applyBatch(operations);
            }
        });
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE_TV_SHOW:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final FavoriteTvShowDatabase database = FavoriteTvShowDatabase.getInstance(context);
                final FavoriteTvShow[] favoriteTvShows = new FavoriteTvShow[values.length];
                for (int i = 0; i < values.length; i++) {
                    favoriteTvShows[i] = FavoriteTvShow.fromContentValues(values[i]);
                }
                return database.favoriteTvShowDao().insertAllFavoriteTvShows(favoriteTvShows).length;
            case FAVORITE_TV_SHOW_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
