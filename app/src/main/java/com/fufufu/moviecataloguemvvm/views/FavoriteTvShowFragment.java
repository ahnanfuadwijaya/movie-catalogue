package com.fufufu.moviecataloguemvvm.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.fufufu.moviecataloguemvvm.adapters.FavoriteTvShowAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFavoriteTvShowBinding;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.FavoriteTvShow;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteFilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteTvShowViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {

    private FavoriteTvShowViewModel favoriteTvShowViewModel;
    private FavoriteTvShowAdapter favoriteTvShowAdapter;


    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentFavoriteTvShowBinding fragmentFavoriteTvShowBinding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = fragmentFavoriteTvShowBinding.rvFavoriteTvShowList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProgressBar progressBar = fragmentFavoriteTvShowBinding.progressBarFavoriteTvShow;
        favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        favoriteTvShowAdapter = new FavoriteTvShowAdapter(this);
        recyclerView.setAdapter(favoriteTvShowAdapter);
        favoriteTvShowViewModel.getAllFavoriteTvShows().observe(this, new Observer<List<FavoriteTvShow>>() {
            @Override
            public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
                favoriteTvShowAdapter.setFavoriteTvShows(favoriteTvShows);
            }
        });
        favoriteTvShowViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return fragmentFavoriteTvShowBinding.getRoot();
    }
}
