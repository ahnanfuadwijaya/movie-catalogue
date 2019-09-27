package com.fufufu.favoritefilm.viewmodels;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.repository.Repository;
import java.util.ArrayList;


public class FavoriteFilmViewModel extends AndroidViewModel {
    private Repository repository;
    private Cursor allFavoriteFilms;
    private MutableLiveData<ArrayList<FavoriteFilm>> arrayListLiveData = new MutableLiveData<>();

    public FavoriteFilmViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allFavoriteFilms = null;
        allFavoriteFilms = repository.getAllFavoriteFilms();
        if(allFavoriteFilms != null){
            Log.d("allFavoriteFilms", "not null");
            Log.d("size", String.valueOf(allFavoriteFilms.getCount()));
        }
    }

    public LiveData<ArrayList<FavoriteFilm>> getAllFavoriteFilms() {
        mapCursorToArrayList(allFavoriteFilms);
        return arrayListLiveData;
    }

    private void mapCursorToArrayList(Cursor cursor){
        if(cursor != null){
            ArrayList<FavoriteFilm> favoriteFilms = new ArrayList<>();
            if(cursor.moveToFirst()){
                do{
                    long id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
                    Float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("voteAverage"));
                    favoriteFilms.add(new FavoriteFilm(id, posterPath, title, voteAverage));
                    Log.d("Crsrmapping,item:title", title);
                }while (cursor.moveToNext());
                cursor.close();
            }
            arrayListLiveData.setValue(favoriteFilms);
        }
    }

    public void deleteFavoriteFilm(long id) {
        repository.deleteFavoriteFilm(id);
    }

    public LiveData<Boolean> isLoading() {
        return repository.getLoading();
    }
}