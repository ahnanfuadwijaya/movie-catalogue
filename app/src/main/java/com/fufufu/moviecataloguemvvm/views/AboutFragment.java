package com.fufufu.moviecataloguemvvm.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fufufu.moviecataloguemvvm.databinding.FragmentAboutBinding;
import com.fufufu.moviecataloguemvvm.viewmodels.AboutViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Hardcode Sementara
        Objects.requireNonNull(getActivity()).setTitle("About Me");
        FragmentAboutBinding fragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false);
        AboutViewModel aboutViewModel = new AboutViewModel();
        aboutViewModel.setData();
        fragmentAboutBinding.setAbout(aboutViewModel.getData());
        return fragmentAboutBinding.getRoot();
    }
}
