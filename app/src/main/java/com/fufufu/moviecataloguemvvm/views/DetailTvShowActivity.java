package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailFilmBinding;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailTvShowBinding;
import com.fufufu.moviecataloguemvvm.models.FavoriteFilm;
import com.fufufu.moviecataloguemvvm.models.FavoriteTvShow;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.viewmodels.DetailFilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.DetailTvShowViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteFilmViewModel;
import com.fufufu.moviecataloguemvvm.viewmodels.FavoriteTvShowViewModel;

import java.util.Locale;
import java.util.Objects;

public class DetailTvShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        final int tvShowId = getIntent().getIntExtra("tvShowId", 0);
        final DetailTvShowViewModel detailTvShowViewModel = ViewModelProviders.of(this).get(DetailTvShowViewModel.class);
        final FavoriteTvShowViewModel favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        final ActivityDetailTvShowBinding activityDetailTvShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv_show);
        detailTvShowViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.VISIBLE);
                }
                else {
                    activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.GONE);
                }
            }
        });
        if(favoriteTvShowViewModel.getFavoriteTvShow(tvShowId) != null){
            activityDetailTvShowBinding.ivAddFavoriteTvShow.setImageResource(R.drawable.ic_favorite_24px);
            activityDetailTvShowBinding.ivAddFavoriteTvShow.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.design_default_color_error), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        detailTvShowViewModel.getTvShow(tvShowId).observe(this, new Observer<TvShow>() {
            @Override
            public void onChanged(final TvShow tvShow) {
                activityDetailTvShowBinding.setTvShow(tvShow);
                setTitle(tvShow.getName());
                activityDetailTvShowBinding.addToFavoriteTvShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteTvShow favoriteTvShow = new FavoriteTvShow();
                        favoriteTvShow.setId(tvShow.getId());
                        favoriteTvShow.setPosterPath(tvShow.getPosterPath());
                        favoriteTvShow.setVoteAverage(Float.parseFloat(tvShow.getVoteAverage()));
                        favoriteTvShow.setName(tvShow.getName());
                        favoriteTvShowViewModel.insertFavoriteTvShow(favoriteTvShow);
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_add_to_favorite), Toast.LENGTH_LONG).show();
                        activityDetailTvShowBinding.ivAddFavoriteTvShow.setImageResource(R.drawable.ic_favorite_24px);
                        activityDetailTvShowBinding.ivAddFavoriteTvShow.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.design_default_color_error), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                });
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activityDetailTvShowBinding.executePendingBindings();
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //deprecate
        config.locale = locale;
        //deprecate
        this.getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
