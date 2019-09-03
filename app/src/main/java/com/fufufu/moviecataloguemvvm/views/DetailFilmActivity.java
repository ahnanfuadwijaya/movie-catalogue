package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailFilmBinding;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.DetailFilmViewModel;
import java.util.Objects;

public class DetailFilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActivityDetailFilmBinding activityDetailFilmBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        Film film = getIntent().getParcelableExtra("film");
        DetailFilmViewModel detailFilmViewModel = new DetailFilmViewModel();
        detailFilmViewModel.setFilm(film);
        activityDetailFilmBinding.setFilm(detailFilmViewModel.getFilm());
        setTitle(detailFilmViewModel.getFilm().getTitle());
        activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.GONE);
        activityDetailFilmBinding.executePendingBindings();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}