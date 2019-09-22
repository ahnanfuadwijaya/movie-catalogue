package com.fufufu.moviecatalogue.views;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.databinding.ActivityDetailFilmBinding;
import com.fufufu.moviecatalogue.models.FavoriteFilm;
import com.fufufu.moviecatalogue.models.Film;
import com.fufufu.moviecatalogue.viewmodels.DetailFilmViewModel;
import com.fufufu.moviecatalogue.viewmodels.FavoriteFilmViewModel;
import java.util.Locale;
import java.util.Objects;

public class DetailFilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        final int filmId = getIntent().getIntExtra("filmId", 0);
        final DetailFilmViewModel detailFilmViewModel = ViewModelProviders.of(this).get(DetailFilmViewModel.class);
        final FavoriteFilmViewModel favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        final ActivityDetailFilmBinding activityDetailFilmBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        detailFilmViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.VISIBLE);
                } else {
                    activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.GONE);
                }
            }
        });
        if (favoriteFilmViewModel.getFavoriteFilm(filmId) != null) {
            Drawable favorite = getResources().getDrawable(R.drawable.ic_favorite_red_24dp, null);
            favorite.setBounds(8, 0, 0, 0);
            activityDetailFilmBinding.btnAddToFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, favorite, null);
        }
        detailFilmViewModel.getFilm(filmId).observe(this, new Observer<Film>() {
            @Override
            public void onChanged(final Film film) {
                activityDetailFilmBinding.setFilm(film);
                setTitle(film.getTitle());
                activityDetailFilmBinding.btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteFilm favoriteFilm = new FavoriteFilm();
                        favoriteFilm.setId(film.getId());
                        favoriteFilm.setPosterPath(film.getPosterPath());
                        favoriteFilm.setVoteAverage(Float.parseFloat(film.getVoteAverage()));
                        favoriteFilm.setTitle(film.getTitle());
                        favoriteFilmViewModel.insertFavoriteFilm(favoriteFilm);
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.detail_film_toast_add_to_favorite_film), Toast.LENGTH_LONG).show();

                        Drawable favorite = getResources().getDrawable(R.drawable.ic_favorite_red_24dp, null);
                        favorite.setBounds(8, 0, 0, 0);
                        activityDetailFilmBinding.btnAddToFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, favorite, null);
                    }
                });
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activityDetailFilmBinding.executePendingBindings();
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    @SuppressWarnings({"RedundantSuppression","deprecation"})
    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
