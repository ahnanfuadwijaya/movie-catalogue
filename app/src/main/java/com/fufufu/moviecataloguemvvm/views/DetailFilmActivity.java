package com.fufufu.moviecataloguemvvm.views;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailFilmBinding;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.DetailFilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteFilmViewModel;

import java.util.Objects;

public class DetailFilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        final ActivityDetailFilmBinding activityDetailFilmBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        final Film film = getIntent().getParcelableExtra("film");
        DetailFilmViewModel detailFilmViewModel = ViewModelProviders.of(this).get(DetailFilmViewModel.class);
        detailFilmViewModel.setFilm(film);
        final FavoriteFilmViewModel favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        activityDetailFilmBinding.setFilm(detailFilmViewModel.getFilm());
        setTitle(detailFilmViewModel.getFilm().getTitle());

        activityDetailFilmBinding.btnAddToFavoriteFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteFilm favoriteFilm = new FavoriteFilm();
                favoriteFilm.setFilmId(film.getId());
                favoriteFilm.setOriginalTitle(film.getOriginalTitle());
                favoriteFilm.setOverview(film.getOverview());
                favoriteFilm.setPosterPath(film.getPosterPath());
                favoriteFilm.setReleaseDate(film.getReleaseDate());
                favoriteFilm.setVoteAverage(Float.parseFloat(film.getVoteAverage()));
                favoriteFilm.setTitle(film.getTitle());
                favoriteFilmViewModel.insertFavoriteFilm(favoriteFilm);
                Toast.makeText(getBaseContext(), "Add to Favorite", Toast.LENGTH_LONG).show();
                activityDetailFilmBinding.btnAddToFavoriteFilm.setBackgroundColor(Color.DKGRAY);
                activityDetailFilmBinding.btnAddToFavoriteFilm.setTextColor(Color.WHITE);
            }
        });

        activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.GONE);
        activityDetailFilmBinding.executePendingBindings();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
