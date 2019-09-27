package com.fufufu.favoritefilm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.databinding.ActivityDetailFilmBinding;
import com.fufufu.favoritefilm.models.Film;
import com.fufufu.favoritefilm.viewmodels.DetailFilmViewModel;

import java.util.Locale;
import java.util.Objects;

public class DetailFilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        final long filmId = getIntent().getLongExtra("filmId", 0);
        final DetailFilmViewModel detailFilmViewModel = ViewModelProviders.of(this).get(DetailFilmViewModel.class);
        final ActivityDetailFilmBinding activityDetailFilmBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        detailFilmViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == null) {
                    activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.VISIBLE);
                } else {
                    activityDetailFilmBinding.progressBarDetailFilm.setVisibility(View.GONE);
                }
            }
        });
        detailFilmViewModel.getFilm(filmId).observe(this, new Observer<Film>() {
            @Override
            public void onChanged(final Film film) {
                activityDetailFilmBinding.setFilm(film);
                setTitle(film.getTitle());
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
