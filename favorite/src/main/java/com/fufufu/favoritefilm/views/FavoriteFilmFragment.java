package com.fufufu.favoritefilm.views;

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
import com.fufufu.favoritefilm.adapters.FavoriteFilmAdapter;
import com.fufufu.favoritefilm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.viewmodels.FavoriteFilmViewModel;
import java.util.List;

public class FavoriteFilmFragment extends Fragment {
    private FavoriteFilmAdapter favoriteFilmAdapter;

    public FavoriteFilmFragment() {
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
        favoriteFilmViewModel.getAllFavoriteFilms().observe(this, new Observer<List<FavoriteFilm>>() {
            @Override
            public void onChanged(List<FavoriteFilm> favoriteFilms) {
                favoriteFilmAdapter.setFavoriteFilms(favoriteFilms);
            }
        });
        favoriteFilmViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == null) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return fragmentFavoriteFilmBinding.getRoot();
    }
}
