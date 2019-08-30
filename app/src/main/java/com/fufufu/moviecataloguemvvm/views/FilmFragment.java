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
import com.fufufu.moviecataloguemvvm.databinding.FragmentFilmBinding;
import com.fufufu.moviecataloguemvvm.databinding.FragmentHomeBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.FilmViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment{
    private FilmViewModel filmViewModel;
    private FilmAdapter filmAdapter;


    public FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //FragmentFilmBinding fragmentFilmBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_film);
        final FragmentFilmBinding fragmentFilmBinding = FragmentFilmBinding.inflate(inflater, container, false);

        // bind RecyclerView
        RecyclerView recyclerView = fragmentFilmBinding.rvDaftarFilm;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final ProgressBar progressBar = fragmentFilmBinding.progressBarFilm;

        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);

        filmViewModel.isLoading().observe(this, new Observer<Boolean>() {
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

        filmAdapter = new FilmAdapter();
        recyclerView.setAdapter(filmAdapter);
        getFilms();

        Log.d("FilmFragment", "onCreateView");

        return fragmentFilmBinding.getRoot();
    }


    private void getFilms() {
        filmViewModel.getFilmList().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Film> films) {
                filmAdapter.setFilms(films);
            }
        });
    }


}
