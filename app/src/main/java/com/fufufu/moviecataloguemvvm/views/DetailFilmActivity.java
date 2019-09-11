package com.fufufu.moviecataloguemvvm.views;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailFilmBinding;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.viewmodels.DetailFilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteFilmViewModel;
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
            activityDetailFilmBinding.ivAddFavoriteFilm.setImageResource(R.drawable.ic_favorite_24px);
            activityDetailFilmBinding.ivAddFavoriteFilm.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.design_default_color_error), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        detailFilmViewModel.getFilm(filmId).observe(this, new Observer<Film>() {
            @Override
            public void onChanged(final Film film) {
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
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_add_to_favorite), Toast.LENGTH_LONG).show();
                        activityDetailFilmBinding.ivAddFavoriteFilm.setImageResource(R.drawable.ic_favorite_24px);
                        activityDetailFilmBinding.ivAddFavoriteFilm.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.design_default_color_error), android.graphics.PorterDuff.Mode.SRC_IN);
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
