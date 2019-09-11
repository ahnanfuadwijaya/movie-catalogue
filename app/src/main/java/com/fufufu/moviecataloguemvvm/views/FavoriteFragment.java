package com.fufufu.moviecataloguemvvm.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.adapters.TabFavoriteAdapter;
import com.fufufu.moviecataloguemvvm.adapters.TabHomeAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.fragment_favorite_title));
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_favorite);
        final ViewPager viewPager = view.findViewById(R.id.viewpager_favorite);
        final TabFavoriteAdapter tabFavoriteAdapter = new TabFavoriteAdapter(getChildFragmentManager(), requireContext());
        viewPager.setAdapter(tabFavoriteAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }
}
