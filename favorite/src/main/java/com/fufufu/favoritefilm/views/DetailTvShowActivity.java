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
import com.fufufu.favoritefilm.databinding.ActivityDetailTvShowBinding;
import com.fufufu.favoritefilm.models.TvShow;
import com.fufufu.favoritefilm.viewmodels.DetailTvShowViewModel;
import java.util.Locale;
import java.util.Objects;

public class DetailTvShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        final long tvShowId = getIntent().getLongExtra("tvShowId", 0);
        final DetailTvShowViewModel detailTvShowViewModel = ViewModelProviders.of(this).get(DetailTvShowViewModel.class);
        final ActivityDetailTvShowBinding activityDetailTvShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv_show);
        detailTvShowViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == null) {
                    activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.VISIBLE);
                } else {
                    activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.GONE);
                }
            }
        });
        detailTvShowViewModel.getTvShow(tvShowId).observe(this, new Observer<TvShow>() {
            @Override
            public void onChanged(final TvShow tvShow) {
                activityDetailTvShowBinding.setTvShow(tvShow);
                setTitle(tvShow.getName());
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
