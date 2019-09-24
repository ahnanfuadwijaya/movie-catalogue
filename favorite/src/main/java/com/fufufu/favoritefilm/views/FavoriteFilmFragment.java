package com.fufufu.favoritefilm.views;


import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.adapters.FavoriteFilmAdapter;
import com.fufufu.favoritefilm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.providers.FavoriteFilmContentProvider;
import com.fufufu.favoritefilm.viewmodels.FavoriteFilmViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteFilmFragment extends Fragment {
    private static final int LOADER_FAVORITE_FILM = 1;
    private FavoriteFilmAdapter favoriteFilmAdapter;
    private FavoriteFilmViewModel favoriteFilmViewModel;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFavoriteFilmBinding fragmentFavoriteFilmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_film, container, false);
        favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        favoriteFilmAdapter = new FavoriteFilmAdapter(this);
        fragmentFavoriteFilmBinding.rvFavoriteFilmList.setAdapter(favoriteFilmAdapter);

        favoriteFilmViewModel.getAllFavoriteFilmsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FavoriteFilm>>() {
            @Override
            public void onChanged(ArrayList<FavoriteFilm> favoriteFilms) {
                favoriteFilmAdapter.setFavoriteFilms(favoriteFilms);
            }
        });
        //Objects.requireNonNull(this.getActivity()).getSupportLoaderManager().initLoader(LOADER_FAVORITE_FILM, null, cursorLoaderCallbacks);
        return fragmentFavoriteFilmBinding.getRoot();
    }
    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            if (id == LOADER_FAVORITE_FILM) {
                return new CursorLoader(requireContext(), FavoriteFilmContentProvider.URI_FAVORITE_FILM,
                        new String[]{FavoriteFilm.COLUMN_ID, FavoriteFilm.COLUMN_POSTER_PATH, FavoriteFilm.COLUMN_TITLE, FavoriteFilm.COLUMN_VOTE_AVERAGE}, null, null, null);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if (loader.getId() == LOADER_FAVORITE_FILM) {
                favoriteFilmViewModel.setAllFavoriteFilmsLiveData(data);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            if (loader.getId() == LOADER_FAVORITE_FILM) {
                favoriteFilmViewModel.setAllFavoriteFilmsLiveData(null);
            }
        }
    };
}
