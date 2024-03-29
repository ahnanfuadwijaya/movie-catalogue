package com.fufufu.moviecatalogue.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecatalogue.adapters.TvShowAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentTvShowBinding;
import com.fufufu.moviecatalogue.models.TvShow;
import com.fufufu.moviecatalogue.viewmodels.TvShowViewModel;
import java.util.ArrayList;

public class TvShowFragment extends Fragment {
    private TvShowViewModel tvViewModel;
    private TvShowAdapter tvShowAdapter;

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTvShowBinding fragmentTvShowBinding = FragmentTvShowBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = fragmentTvShowBinding.rvTvShowList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProgressBar progressBar = fragmentTvShowBinding.progressBarTvShow;
        tvViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowAdapter = new TvShowAdapter();
        recyclerView.setAdapter(tvShowAdapter);
        tvViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        getTvShows();
        return fragmentTvShowBinding.getRoot();
    }

    private void getTvShows() {
        tvViewModel.getTvShowList().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
                tvShowAdapter.setTvShows(tvShows);
            }
        });
    }
}