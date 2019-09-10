package com.fufufu.moviecataloguemvvm.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fufufu.moviecataloguemvvm.adapters.FavoriteFilmAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteFilmViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFilmFragment extends Fragment {

    private FavoriteFilmViewModel favoriteFilmViewModel;
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
        favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        favoriteFilmViewModel.getAllFavoriteFilms().observe(this, new Observer<List<FavoriteFilm>>() {
            @Override
            public void onChanged(List<FavoriteFilm> favoriteFilms) {
                favoriteFilmAdapter.setFavoriteFilms(favoriteFilms);
            }
        });
        favoriteFilmAdapter = new FavoriteFilmAdapter(this);
        recyclerView.setAdapter(favoriteFilmAdapter);
        return fragmentFavoriteFilmBinding.getRoot();
    }
}
