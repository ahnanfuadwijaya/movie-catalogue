package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.views.FavoriteFilmFragment;
import com.fufufu.moviecataloguemvvm.views.FavoriteFragment;

public class TabFavoriteAdapter extends androidx.fragment.app.FragmentStatePagerAdapter {
    private final static int PAGE_COUNT = 2;
    private String[] tabTitles;

    public TabFavoriteAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabTitles = context.getResources().getStringArray(R.array.tab_favorite_title);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        fragment = new Fragment();
        if(position == 0){
            fragment = new FavoriteFilmFragment();
        }
        else{
            //fragment = new TvShowFragment();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
