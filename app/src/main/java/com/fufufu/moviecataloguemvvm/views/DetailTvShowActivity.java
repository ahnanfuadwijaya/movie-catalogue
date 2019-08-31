package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivityDetailTvShowBinding;
import com.fufufu.moviecataloguemvvm.models.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailTvShowBinding activityDetailTvShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv_show);

        TvShow tvShow = getIntent().getParcelableExtra("tvShow");
        activityDetailTvShowBinding.setTvShow(tvShow);


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
