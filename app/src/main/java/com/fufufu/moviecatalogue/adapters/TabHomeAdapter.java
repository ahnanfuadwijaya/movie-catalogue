package com.fufufu.moviecatalogue.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.views.FilmFragment;
import com.fufufu.moviecatalogue.views.TvShowFragment;

public class TabHomeAdapter extends androidx.fragment.app.FragmentStatePagerAdapter {
    private final static int PAGE_COUNT = 2;
    private String[] tabTitles;

    public TabHomeAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabTitles = context.getResources().getStringArray(R.array.tab_home_title);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new FilmFragment();
        } else {
            fragment = new TvShowFragment();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
