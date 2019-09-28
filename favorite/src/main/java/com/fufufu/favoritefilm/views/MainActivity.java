package com.fufufu.favoritefilm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.adapters.TabFavoriteAdapter;
import com.fufufu.favoritefilm.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationContext();
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TabLayout tabLayout = activityMainBinding.tabFavorite;
        ViewPager viewPager = activityMainBinding.viewpagerFavorite;
        TabFavoriteAdapter tabFavoriteAdapter = new TabFavoriteAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabFavoriteAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
