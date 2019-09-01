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
import com.fufufu.moviecataloguemvvm.adapters.TvShowAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentTvShowBinding;
import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.viewmodels.TvShowViewModel;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment{
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
                if(aBoolean){
                    progressBar.setVisibility(View.GONE);
                }
                else {
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