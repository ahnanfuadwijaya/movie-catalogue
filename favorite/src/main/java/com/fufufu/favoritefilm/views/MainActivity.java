package com.fufufu.favoritefilm.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.fufufu.favoritefilm.R;
import com.fufufu.favoritefilm.adapters.TabFavoriteAdapter;
import com.fufufu.favoritefilm.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TabLayout tabLayout = activityMainBinding.tabFavorite;
        ViewPager viewPager = activityMainBinding.viewpagerFavorite;
        TabFavoriteAdapter tabFavoriteAdapter = new TabFavoriteAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabFavoriteAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public static Context getContextOfApplication()
    {
        return context;
    }
}
