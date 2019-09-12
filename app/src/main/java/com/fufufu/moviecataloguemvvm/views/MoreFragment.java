package com.fufufu.moviecataloguemvvm.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.FragmentMoreBinding;

import java.util.Objects;

public class MoreFragment extends Fragment {


    public MoreFragment() {
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
        inflater.inflate(R.menu.action_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final FragmentMoreBinding fragmentSettingBinding = FragmentMoreBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).setTitle(R.string.more_bottom_nav_title);
        return fragmentSettingBinding.getRoot();
    }

}
