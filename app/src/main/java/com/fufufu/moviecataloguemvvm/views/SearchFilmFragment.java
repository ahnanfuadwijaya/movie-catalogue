package com.fufufu.moviecataloguemvvm.views;


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
import android.widget.ProgressBar;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.adapters.FilmAdapter;
import com.fufufu.moviecataloguemvvm.databinding.FragmentFilmBinding;
import com.fufufu.moviecataloguemvvm.databinding.FragmentSearchFilmBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.FilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.SearchFilmViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFilmFragment extends Fragment {
    private FragmentSearchFilmBinding fragmentSearchFilmBinding;
    private FilmAdapter filmAdapter;
    private SearchFilmViewModel searchFilmViewModel;
    private String lang = "";
    private String query = "";
    public SearchFilmFragment() {
        // Required empty public constructor
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
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.action_search, menu);
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
                fragmentSearchFilmBinding.progressBarFilm.setVisibility(View.VISIBLE);
                searchFilmViewModel.setMutableFilmResult(lang, query);
                searchFilmViewModel.getFilmResult().observe(SearchFilmFragment.this, new Observer<ArrayList<Film>>() {
                    @Override
                    public void onChanged(ArrayList<Film> films) {
                        filmAdapter.setFilms(films);
                        fragmentSearchFilmBinding.progressBarFilm.setVisibility(View.GONE);
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
        fragmentSearchFilmBinding = FragmentSearchFilmBinding.inflate(inflater, container, false);
        fragmentSearchFilmBinding.progressBarFilm.setVisibility(View.GONE);
        RecyclerView recyclerView = fragmentSearchFilmBinding.rvFilmList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        searchFilmViewModel = ViewModelProviders.of(this).get(SearchFilmViewModel.class);
        filmAdapter = new FilmAdapter();
        recyclerView.setAdapter(filmAdapter);
        return fragmentSearchFilmBinding.getRoot();
    }

    private void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        lang = prefs.getString(langPref, "");
    }

}
