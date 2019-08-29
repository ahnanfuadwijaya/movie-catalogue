package com.fufufu.moviecataloguemvvm.adapters;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.views.FilmFragment;
import com.fufufu.moviecataloguemvvm.views.TvShowFragment;

public class TabHomeAdapter extends androidx.fragment.app.FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[];

    public TabHomeAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabTitles = context.getResources().getStringArray(R.array.tab_title);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        if(position == 0){
            //Show Film Fragment
            fragment = new FilmFragment();
        }
        else{
            //Show TV Show Fragment
            fragment = new TvShowFragment();
        }
        Log.d("Tab", "Tab Layout");
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
