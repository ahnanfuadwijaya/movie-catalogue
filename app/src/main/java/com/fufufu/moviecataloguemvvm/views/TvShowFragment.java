package com.fufufu.moviecataloguemvvm.views;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.adapters.FilmAdapter;
import com.fufufu.moviecataloguemvvm.adapters.TvShowAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFilmBinding;
import com.fufufu.moviecataloguemvvm.databinding.FragmentTvShowBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.viewmodels.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements LifecycleOwner {
    private TvShowViewModel tvViewModel;
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBar;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        progressBar = view.findViewById(R.id.progress_bar_tv_show);

        FragmentTvShowBinding fragmentTvShowBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_tv_show);

        // bind RecyclerView
        RecyclerView recyclerView = fragmentTvShowBinding.rvDaftarTvShow;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        tvViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowAdapter = new TvShowAdapter();
        recyclerView.setAdapter(tvShowAdapter);

        getTvShows();
        // Inflate the layout for this fragment
        Log.d("FilmFragment", "onCreateView");
        return view;
    }

    private void getTvShows() {
        tvViewModel.getTvShowList().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
                tvShowAdapter.setTvShows(tvShows);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
