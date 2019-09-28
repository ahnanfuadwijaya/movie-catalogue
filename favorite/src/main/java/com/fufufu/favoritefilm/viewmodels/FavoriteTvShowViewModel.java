package com.fufufu.favoritefilm.viewmodels;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.models.FavoriteTvShow;
import com.fufufu.favoritefilm.repository.Repository;

import java.util.ArrayList;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private Repository repository;
    private Cursor allFavoriteTvShows;
    private MutableLiveData<ArrayList<FavoriteTvShow>> arrayListLiveData = new MutableLiveData<>();

    public FavoriteTvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
        allFavoriteTvShows = null;
        allFavoriteTvShows = repository.getAllFavoriteTvShows();
        if(allFavoriteTvShows != null){
            Log.d("allFavoriteFilms", "not null");
            Log.d("size", String.valueOf(allFavoriteTvShows.getCount()));
        }
    }

    public LiveData<ArrayList<FavoriteTvShow>> getAllFavoriteTvShows() {
        mapCursorToArrayList(allFavoriteTvShows);
        return arrayListLiveData;
    }

    private void mapCursorToArrayList(Cursor cursor){
        if(cursor != null){
            ArrayList<FavoriteTvShow> favoriteTvShows = new ArrayList<>();
            if(cursor.moveToFirst()){
                do{
                    long id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
                    Float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("voteAverage"));
                    favoriteTvShows.add(new FavoriteTvShow(id, posterPath, name, voteAverage));
                    Log.d("Crsrmapping,item:name", name);
                }while (cursor.moveToNext());
                cursor.close();
            }
            arrayListLiveData.setValue(favoriteTvShows);
        }
    }

    public void deleteFavoriteTvShow(long id) {
        repository.deleteFavoriteTvShow(id);
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}
