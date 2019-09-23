package com.fufufu.moviecatalogue.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.databinding.FragmentAboutBinding;
import com.fufufu.moviecatalogue.viewmodels.AboutViewModel;
import java.util.Objects;


public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.fragment_about_me_title));
        FragmentAboutBinding fragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false);
        AboutViewModel aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        aboutViewModel.setData();
        fragmentAboutBinding.setAbout(aboutViewModel.getData());
        return fragmentAboutBinding.getRoot();
    }
}
