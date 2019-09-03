package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailTvShowBinding;
import com.fufufu.moviecataloguemvvm.models.TvShow;
import com.fufufu.moviecataloguemvvm.viewmodels.DetailTvShowViewModel;

import java.util.Objects;

public class DetailTvShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActivityDetailTvShowBinding activityDetailTvShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv_show);
        TvShow tvShow = getIntent().getParcelableExtra("tvShow");
        DetailTvShowViewModel detailTvShowViewModel = new DetailTvShowViewModel();
        detailTvShowViewModel.setTvShow(tvShow);
        activityDetailTvShowBinding.setTvShow(detailTvShowViewModel.getTvShow());
        setTitle(detailTvShowViewModel.getTvShow().getName());
        activityDetailTvShowBinding.progressBarDetailTvShow.setVisibility(View.GONE);
        activityDetailTvShowBinding.executePendingBindings();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
