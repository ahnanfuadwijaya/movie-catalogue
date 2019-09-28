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
import com.fufufu.moviecatalogue.databinding.ActivityDetailTvShowBinding;
import com.fufufu.moviecatalogue.models.FavoriteTvShow;
import com.fufufu.moviecatalogue.models.TvShow;
import com.fufufu.moviecatalogue.viewmodels.DetailTvShowViewModel;
import com.fufufu.moviecatalogue.viewmodels.FavoriteTvShowViewModel;
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
                if (!aBoolean) {
                    activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.VISIBLE);
                } else {
                    activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.GONE);
                }
            }
        });
        if (favoriteTvShowViewModel.getFavoriteTvShow(tvShowId).getCount() != 0) {
            Drawable favorite = getResources().getDrawable(R.drawable.ic_favorite_red_24dp, null);
            favorite.setBounds(8, 0, 0, 0);
            activityDetailTvShowBinding.btnAddToFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, favorite, null);
        }
        detailTvShowViewModel.getTvShow(tvShowId).observe(this, new Observer<TvShow>() {
            @Override
            public void onChanged(final TvShow tvShow) {
                activityDetailTvShowBinding.setTvShow(tvShow);
                setTitle(tvShow.getName());
                activityDetailTvShowBinding.btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteTvShow favoriteTvShow = new FavoriteTvShow();
                        favoriteTvShow.setId(tvShow.getId());
                        favoriteTvShow.setPosterPath(tvShow.getPosterPath());
                        favoriteTvShow.setVoteAverage(Float.parseFloat(tvShow.getVoteAverage()));
                        favoriteTvShow.setName(tvShow.getName());
                        favoriteTvShowViewModel.insertFavoriteTvShow(favoriteTvShow);
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.detail_tv_show_toast_add_to_favorite_tv_show), Toast.LENGTH_LONG).show();

                        Drawable favorite = getResources().getDrawable(R.drawable.ic_favorite_red_24dp, null);
                        favorite.setBounds(8, 0, 0, 0);
                        activityDetailTvShowBinding.btnAddToFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, favorite, null);
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
