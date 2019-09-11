package com.fufufu.moviecataloguemvvm.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.FragmentAboutBinding;
import com.fufufu.moviecataloguemvvm.viewmodels.AboutViewModel;
import java.util.Objects;


public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.fragment_about_me_title));
        FragmentAboutBinding fragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false);
        AboutViewModel aboutViewModel = new AboutViewModel();
        aboutViewModel.setData();
        fragmentAboutBinding.setAbout(aboutViewModel.getData());
        return fragmentAboutBinding.getRoot();
    }
}
