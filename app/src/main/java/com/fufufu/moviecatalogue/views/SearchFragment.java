package com.fufufu.moviecatalogue.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.adapters.TabSearchAdapter;
import com.fufufu.moviecatalogue.databinding.FragmentSearchBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
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
        /*Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.action_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_item);

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ActiveSearchFragment());
                fragmentTransaction.commit();
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        FragmentSearchBinding fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        TabSearchAdapter tabSearchAdapter = new TabSearchAdapter(getChildFragmentManager(), requireContext());
        fragmentSearchBinding.viewpager.setAdapter(tabSearchAdapter);
        fragmentSearchBinding.tabSearch.setupWithViewPager(fragmentSearchBinding.viewpager, true);
        Objects.requireNonNull(getActivity()).setTitle(R.string.search_bottom_nav_title);
        return fragmentSearchBinding.getRoot();
    }
}
