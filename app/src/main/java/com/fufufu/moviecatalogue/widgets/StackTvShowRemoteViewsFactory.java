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
import com.fufufu.moviecatalogue.dao.FavoriteTvShowDao;
import com.fufufu.moviecatalogue.database.FavoriteTvShowDatabase;
import com.fufufu.moviecatalogue.models.FavoriteFilm;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackTvShowRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<FavoriteTvShow> favoriteTvShows = new ArrayList<>();
    private final Context mContext;
    private FavoriteTvShowDao favoriteTvShowDao;
    private Cursor favoriteTvShowsCursor;

    StackTvShowRemoteViewsFactory(Context context) {
        mContext = context;
        favoriteTvShowDao = FavoriteTvShowDatabase.getInstance(context).favoriteTvShowDao();

    }

    private void getFromRepo(){
        try {
            favoriteTvShowsCursor = new GetAllFavoriteTvShowsAsyncTask(favoriteTvShowDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetAllFavoriteTvShowsAsyncTask extends AsyncTask<Void, Void, Cursor> {
        private FavoriteTvShowDao favoriteTvShowDao;

        GetAllFavoriteTvShowsAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteTvShowDao.getAllFavoriteTvShows();
        }
    }

    @Override
    public void onCreate() {
        reloadData();
    }

    private void reloadData(){
        getFromRepo();
        if(favoriteTvShowsCursor != null){
            favoriteTvShows.clear();
            if(favoriteTvShowsCursor.moveToFirst()){
                do{
                    long id = favoriteTvShowsCursor.getInt(favoriteTvShowsCursor.getColumnIndexOrThrow("id"));
                    String name = favoriteTvShowsCursor.getString(favoriteTvShowsCursor.getColumnIndexOrThrow("name"));
                    String posterPath = favoriteTvShowsCursor.getString(favoriteTvShowsCursor.getColumnIndexOrThrow("posterPath"));
                    Float voteAverage = favoriteTvShowsCursor.getFloat(favoriteTvShowsCursor.getColumnIndexOrThrow("voteAverage"));
                    favoriteTvShows.add(new FavoriteTvShow(id, posterPath, name, voteAverage));
                }while (favoriteTvShowsCursor.moveToNext());
                favoriteTvShowsCursor.close();
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
        return favoriteTvShows.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorite_tv_show_widget_item);

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(favoriteTvShows.get(i).getPosterPath())
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.iv_favorite_tv_show_widget, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.iv_favorite_tv_show_widget, fillInIntent);



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
