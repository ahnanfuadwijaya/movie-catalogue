package com.fufufu.moviecatalogue.views;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
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
import com.fufufu.moviecatalogue.adapters.FilmAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentFilmBinding;
import com.fufufu.moviecatalogue.models.Film;
import com.fufufu.moviecatalogue.viewmodels.FilmViewModel;
import java.util.ArrayList;

public class FilmFragment extends Fragment {
    private FilmViewModel filmViewModel;
    private FilmAdapter filmAdapter;

    public FilmFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentFilmBinding fragmentFilmBinding = FragmentFilmBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = fragmentFilmBinding.rvFilmList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProgressBar progressBar = fragmentFilmBinding.progressBarFilm;
        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        filmAdapter = new FilmAdapter();
        recyclerView.setAdapter(filmAdapter);
        getFilms();
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
