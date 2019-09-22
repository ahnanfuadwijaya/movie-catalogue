package com.fufufu.moviecatalogue.views;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.adapters.TvShowAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentSearchTvShowBinding;
import com.fufufu.moviecatalogue.models.TvShow;
import com.fufufu.moviecatalogue.viewmodels.SearchTvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class SearchTvShowFragment extends Fragment {
    private FragmentSearchTvShowBinding fragmentSearchTvShowBinding;
    private TvShowAdapter tvShowAdapter;
    private SearchTvShowViewModel searchTvShowViewModel;
    private String lang;
    private String query;


    public SearchTvShowFragment() {
        // Required empty public constructor
        lang = "";
        query = "";
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_language);
        if(menuItem != null){
            menuItem.setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_item);

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new ActiveSearchFragment());
//                fragmentTransaction.commit();
                return false;
            }
        });

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("Search", s);
                loadLocale();
                query = s;
                fragmentSearchTvShowBinding.progressBarTvShow.setVisibility(View.VISIBLE);
                searchTvShowViewModel.setMutableTvShowResult(lang, query);
                searchTvShowViewModel.getTvShowmResult().observe(SearchTvShowFragment.this, new Observer<ArrayList<TvShow>>() {
                    @Override
                    public void onChanged(ArrayList<TvShow> tvShows) {
                        tvShowAdapter.setTvShows(tvShows);
                        fragmentSearchTvShowBinding.progressBarTvShow.setVisibility(View.GONE);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        fragmentSearchTvShowBinding = FragmentSearchTvShowBinding.inflate(inflater, container, false);
        fragmentSearchTvShowBinding.progressBarTvShow.setVisibility(View.GONE);
        RecyclerView recyclerView = fragmentSearchTvShowBinding.rvTvShowList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        searchTvShowViewModel = ViewModelProviders.of(this).get(SearchTvShowViewModel.class);
        tvShowAdapter = new TvShowAdapter();
        recyclerView.setAdapter(tvShowAdapter);
        return fragmentSearchTvShowBinding.getRoot();
    }

    private void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        lang = prefs.getString(langPref, "");
    }
}
