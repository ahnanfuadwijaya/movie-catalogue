package com.fufufu.moviecataloguemvvm.views;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
        final int filmId = getIntent().getIntExtra("filmId", 0);
        final DetailFilmViewModel detailFilmViewModel = ViewModelProviders.of(this).get(DetailFilmViewModel.class);
        final FavoriteFilmViewModel favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        final ActivityDetailFilmBinding activityDetailFilmBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        detailFilmViewModel.getFilm(filmId).observe(this, new Observer<Film>() {
            @Override
            public void onChanged(final Film film) {
                detailFilmViewModel.setFilm(filmId);
                activityDetailFilmBinding.setFilm(film);

                setTitle(film.getTitle());

                activityDetailFilmBinding.addToFavoriteFilm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteFilm favoriteFilm = new FavoriteFilm();
                        favoriteFilm.setId(film.getId());
                        favoriteFilm.setPosterPath(film.getPosterPath());
                        favoriteFilm.setVoteAverage(Float.parseFloat(film.getVoteAverage()));
                        favoriteFilm.setTitle(film.getTitle());
                        favoriteFilmViewModel.insertFavoriteFilm(favoriteFilm);
                        Toast.makeText(getBaseContext(), "Add to Favorite", Toast.LENGTH_LONG).show();
                        activityDetailFilmBinding.ivAddFavoriteFilm.setImageResource(R.drawable.ic_favorite_24px);
                        activityDetailFilmBinding.ivAddFavoriteFilm.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.design_default_color_error), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                });
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.GONE);
        activityDetailFilmBinding.executePendingBindings();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
