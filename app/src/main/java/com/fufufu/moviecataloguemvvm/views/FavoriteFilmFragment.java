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

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.adapters.FavoriteFilmAdapter;
import com.fufufu.moviecataloguemvvm.adapters.FilmAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFilmBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteFilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.FilmViewModel;

import java.util.ArrayList;

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
//        favoriteFilmViewModel.isLoading().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if(aBoolean){
//                    progressBar.setVisibility(View.GONE);
//                }
//                else {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        favoriteFilmAdapter = new FavoriteFilmAdapter();
        recyclerView.setAdapter(favoriteFilmAdapter);
        getFilms();
        return fragmentFavoriteFilmBinding.getRoot();
    }

    private void getFilms() {
        favoriteFilmViewModel.getFavoriteFilm().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Film> films) {
                favoriteFilmAdapter.setFilms(films);
            }
        });
    }
}