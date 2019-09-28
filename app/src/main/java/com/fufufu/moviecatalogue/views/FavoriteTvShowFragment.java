package com.fufufu.moviecatalogue.views;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fufufu.moviecatalogue.adapters.FavoriteTvShowAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentFavoriteTvShowBinding;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;
import com.fufufu.moviecatalogue.viewmodels.FavoriteTvShowViewModel;
import java.util.ArrayList;

public class FavoriteTvShowFragment extends Fragment {
    private FavoriteTvShowAdapter favoriteTvShowAdapter;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
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
        favoriteTvShowViewModel.getAllFavoriteTvShows().observe(this, new Observer<Cursor>() {
            @Override
            public void onChanged(Cursor cursor) {
                ArrayList<FavoriteTvShow> favoriteTvShows = new ArrayList<>();
                if(cursor.moveToFirst()){
                    do{
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
                        Float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("voteAverage"));
                        favoriteTvShows.add(new FavoriteTvShow(id, posterPath, name, voteAverage));
                    }while (cursor.moveToNext());
                }
                favoriteTvShowAdapter.setFavoriteTvShows(favoriteTvShows);
            }
        });

        favoriteTvShowViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return fragmentFavoriteTvShowBinding.getRoot();
    }
}
