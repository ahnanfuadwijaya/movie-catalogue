package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.adapters.SelectLanguageAdapter;
import com.fufufu.moviecataloguemvvm.databinding.ActivitySetLanguageBinding;

public class SetLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySetLanguageBinding activitySetLanguageBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_language);
        String[] item = getResources().getStringArray(R.array.language_list);
        activitySetLanguageBinding.rvLanguage.setHasFixedSize(true);
        activitySetLanguageBinding.rvLanguage.setLayoutManager(new LinearLayoutManager(this));
        SelectLanguageAdapter selectLanguageAdapter = new SelectLanguageAdapter(item);
        activitySetLanguageBinding.rvLanguage.setAdapter(selectLanguageAdapter);
        setTitle(R.string.language_select);
    }
}
