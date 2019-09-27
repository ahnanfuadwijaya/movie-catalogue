package com.fufufu.favoritefilm.views;


import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.adapters.FavoriteFilmAdapter;
import com.fufufu.favoritefilm.databinding.FragmentFavoriteFilmBinding;
import com.fufufu.favoritefilm.models.FavoriteFilm;
import com.fufufu.favoritefilm.viewmodels.FavoriteFilmViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteFilmFragment extends Fragment {
    private FavoriteFilmAdapter favoriteFilmAdapter;
    private FavoriteFilmViewModel favoriteFilmViewModel;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentFavoriteFilmBinding fragmentFavoriteFilmBinding = FragmentFavoriteFilmBinding.inflate(inflater, container, false);

        Context applicationContext = MainActivity.getContextOfApplication();

        ContentResolver contentResolver = applicationContext.getContentResolver();
        String[] projection = new String[]{"id", "title", "voteAverage", "posterPath"};
        String selection = null;
        String[] selectionArguments = null;
        String sortOrder = null;

        String AUTHORITY = "com.fufufu.moviecatalogue";
        String URL = "content://" + AUTHORITY + "/" + FavoriteFilm.TABLE_NAME;
        Uri uri = Uri.parse(URL);
        String myMimeType = contentResolver.getType(uri);
        Log.d("mimeTypeCR", myMimeType != null ? myMimeType : "null");

        try (Cursor cursor = contentResolver.query(uri, projection, selection, selectionArguments, sortOrder)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Log.d("Cursor", "info:");
                    Log.d("size", String.valueOf(cursor.getCount()));
                }
            }
        }

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
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return fragmentFavoriteFilmBinding.getRoot();
    }
}
