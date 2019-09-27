package com.fufufu.moviecatalogue.views;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecatalogue.adapters.FavoriteFilmAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.moviecatalogue.models.FavoriteFilm;
import com.fufufu.moviecatalogue.viewmodels.FavoriteFilmViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFilmFragment extends Fragment {
    private FavoriteFilmAdapter favoriteFilmAdapter;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentFavoriteFilmBinding fragmentFavoriteFilmBinding = FragmentFavoriteFilmBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = fragmentFavoriteFilmBinding.rvFavoriteFilmList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProgressBar progressBar = fragmentFavoriteFilmBinding.progressBarFavoriteFilm;
        FavoriteFilmViewModel favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        favoriteFilmAdapter = new FavoriteFilmAdapter(this);
        recyclerView.setAdapter(favoriteFilmAdapter);
        favoriteFilmViewModel.getAllFavoriteFilms().observe(this, new Observer<Cursor>() {
            @Override
            public void onChanged(Cursor cursor) {
                Log.d("cursor size", String.valueOf(cursor.getCount()));
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
                }
                favoriteFilmAdapter.setFavoriteFilms(favoriteFilms);
            }
        });


        favoriteFilmViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return fragmentFavoriteFilmBinding.getRoot();
    }
}
