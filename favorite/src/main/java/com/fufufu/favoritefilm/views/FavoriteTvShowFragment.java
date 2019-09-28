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
import com.fufufu.favoritefilm.adapters.FavoriteTvShowAdapter;
import com.fufufu.favoritefilm.databinding.FragmentFavoriteTvShowBinding;
import com.fufufu.favoritefilm.models.FavoriteTvShow;
import com.fufufu.favoritefilm.viewmodels.FavoriteTvShowViewModel;
import java.util.List;

public class FavoriteTvShowFragment extends Fragment {
    private FavoriteTvShowAdapter favoriteTvShowAdapter;

    public FavoriteTvShowFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentFavoriteTvShowBinding fragmentFavoriteTvShowBinding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = fragmentFavoriteTvShowBinding.rvFavoriteTvShowList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProgressBar progressBar = fragmentFavoriteTvShowBinding.progressBarFavoriteTvShow;
        FavoriteTvShowViewModel favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
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
                if (aBoolean == null) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return fragmentFavoriteTvShowBinding.getRoot();
    }

}
