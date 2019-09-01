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
import com.fufufu.moviecataloguemvvm.adapters.FilmAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFilmBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.FilmViewModel;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class FilmFragment extends Fragment{
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
