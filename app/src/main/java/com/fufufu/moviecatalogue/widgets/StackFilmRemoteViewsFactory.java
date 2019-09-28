package com.fufufu.moviecatalogue.widgets;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.dao.FavoriteFilmDao;
import com.fufufu.moviecatalogue.database.FavoriteFilmDatabase;
import com.fufufu.moviecatalogue.models.FavoriteFilm;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackFilmRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<FavoriteFilm> favoriteFilms = new ArrayList<>();
    private final Context mContext;
    private FavoriteFilmDao favoriteFilmDao;
    private Cursor favoriteFilmsCursor;

    StackFilmRemoteViewsFactory(Context context) {
        mContext = context;
        favoriteFilmDao = FavoriteFilmDatabase.getInstance(context).favoriteFilmDao();

    }

    private void getFromRepo(){
        try {
            favoriteFilmsCursor = new GetAllFavoriteFilmsAsyncTask(favoriteFilmDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetAllFavoriteFilmsAsyncTask extends AsyncTask<Void, Void, Cursor> {
        private FavoriteFilmDao favoriteFilmDao;

        GetAllFavoriteFilmsAsyncTask(FavoriteFilmDao favoriteFilmDao) {
            this.favoriteFilmDao = favoriteFilmDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteFilmDao.getAllFavoriteFilms();
        }
    }

    @Override
    public void onCreate() {
        reloadData();
    }

    private void reloadData(){
        getFromRepo();
        if(favoriteFilmsCursor != null){
            favoriteFilms.clear();
            if(favoriteFilmsCursor.moveToFirst()){
                do{
                    long id = favoriteFilmsCursor.getInt(favoriteFilmsCursor.getColumnIndexOrThrow("id"));
                    String title = favoriteFilmsCursor.getString(favoriteFilmsCursor.getColumnIndexOrThrow("title"));
                    String posterPath = favoriteFilmsCursor.getString(favoriteFilmsCursor.getColumnIndexOrThrow("posterPath"));
                    Float voteAverage = favoriteFilmsCursor.getFloat(favoriteFilmsCursor.getColumnIndexOrThrow("voteAverage"));
                    favoriteFilms.add(new FavoriteFilm(id, posterPath, title, voteAverage));
                }while (favoriteFilmsCursor.moveToNext());
                favoriteFilmsCursor.close();
            }
        }
    }

    @Override
    public void onDataSetChanged() {
        reloadData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favoriteFilms.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorite_film_widget_item);

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(favoriteFilms.get(i).getPosterPath())
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteFilmWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);



        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
