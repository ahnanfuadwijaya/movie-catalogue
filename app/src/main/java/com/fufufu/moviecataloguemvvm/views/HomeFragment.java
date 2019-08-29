package com.fufufu.moviecataloguemvvm.views;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.adapters.TabHomeAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_home);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabHomeAdapter(getFragmentManager(), getContext()));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}