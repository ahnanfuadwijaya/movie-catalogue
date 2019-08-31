package com.fufufu.moviecataloguemvvm.views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.FragmentAboutBinding;
import com.fufufu.moviecataloguemvvm.viewmodels.AboutViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);

        // Inflate the layout for this fragment
        FragmentAboutBinding fragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false);
        AboutViewModel aboutViewModel = new AboutViewModel();
        aboutViewModel.setData();
        fragmentAboutBinding.setAbout(aboutViewModel.getData());
        return fragmentAboutBinding.getRoot();
    }

}
