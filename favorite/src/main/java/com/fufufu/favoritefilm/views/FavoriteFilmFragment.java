package com.fufufu.favoritefilm.views;


import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.adapters.FavoriteFilmAdapter;
import com.fufufu.favoritefilm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.providers.FavoriteFilmContentProvider;
import com.fufufu.favoritefilm.viewmodels.FavoriteFilmViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteFilmFragment extends Fragment {
    private static final int LOADER_FAVORITE_FILM = 1;
    private FavoriteFilmAdapter favoriteFilmAdapter;
    private FavoriteFilmViewModel favoriteFilmViewModel;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFavoriteFilmBinding fragmentFavoriteFilmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_film, container, false);
        favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        if(Objects.requireNonNull(favoriteFilmViewModel.getAllFavoriteFilms().getValue().size() == 0)){
            Log.d("Cursor", "kosong");
        }
        else {
            //Log.d("Title", favoriteFilmViewModel.getAllFavoriteFilmsLiveData().getValue().get(0).getTitle());
            Log.d("Cursor", "tidak kosong");
            Log.d("Title[0]", favoriteFilmViewModel.getAllFavoriteFilms().getValue().get(0).getTitle());
        }
        favoriteFilmAdapter = new FavoriteFilmAdapter(this);
        fragmentFavoriteFilmBinding.rvFavoriteFilmList.setAdapter(favoriteFilmAdapter);

        favoriteFilmViewModel.getAllFavoriteFilms().observe(getViewLifecycleOwner(), new Observer<ArrayList<FavoriteFilm>>() {
            @Override
            public void onChanged(ArrayList<FavoriteFilm> favoriteFilms) {
                favoriteFilmAdapter.setFavoriteFilms(favoriteFilms);
            }
        });
        return fragmentFavoriteFilmBinding.getRoot();
    }
}
