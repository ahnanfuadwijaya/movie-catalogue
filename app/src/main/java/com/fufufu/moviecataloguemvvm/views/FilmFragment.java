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
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.FilmViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment implements LifecycleOwner {
    private FilmViewModel filmViewModel;
    private FilmAdapter filmAdapter;
    private ProgressBar progressBar;


    public FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film, container, false);

        progressBar = view.findViewById(R.id.progress_bar_film);

        FragmentFilmBinding fragmentFilmBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_film);

        // bind RecyclerView
        RecyclerView recyclerView = fragmentFilmBinding.rvDaftarFilm;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmAdapter = new FilmAdapter();
        recyclerView.setAdapter(filmAdapter);

        getFilms();
        // Inflate the layout for this fragment
        Log.d("FilmFragment", "onCreateView");
        return view;
    }

    private void getFilms() {
        filmViewModel.getFilmList().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Film> films) {
                filmAdapter.setFilms(films);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
