package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.views.SearchFilmFragment;
import com.fufufu.moviecataloguemvvm.views.SearchTvShowFragment;

public class TabSearchAdapter extends FragmentStatePagerAdapter {
    private int totalPage;
    private String[] tabTitles;

    public TabSearchAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabTitles = context.getResources().getStringArray(R.array.tab_search_title);
        totalPage = tabTitles.length;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        if(position == 0){
            fragment = new SearchFilmFragment();
        }
        else if (position == 1){
            fragment = new SearchTvShowFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return totalPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
