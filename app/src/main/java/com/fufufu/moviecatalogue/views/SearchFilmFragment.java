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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.adapters.FilmAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentSearchFilmBinding;
import com.fufufu.moviecatalogue.models.Film;
import com.fufufu.moviecatalogue.viewmodels.SearchFilmViewModel;
import java.util.ArrayList;
import java.util.Objects;

public class SearchFilmFragment extends Fragment {
    private FragmentSearchFilmBinding fragmentSearchFilmBinding;
    private FilmAdapter filmAdapter;
    private SearchFilmViewModel searchFilmViewModel;
    private String lang;
    private String query;

    public SearchFilmFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
