package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivitySettingBinding;
import com.fufufu.moviecataloguemvvm.services.DailyReminder;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding activitySettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        final DailyReminder dailyReminder = new DailyReminder();
        activitySettingBinding.swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(getApplicationContext(), "Daily Reminder: On", Toast.LENGTH_LONG).show();
                    dailyReminder.setReminder(getApplicationContext(), "Check out latest movies and tv shows today");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Daily Reminder: Off", Toast.LENGTH_LONG).show();
                    dailyReminder.cancelReminder(getApplicationContext());
                }
            }
        });

        activitySettingBinding.gvSetLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SetLanguageActivity.class);
                startActivity(intent);
            }
        });

        setTitle(R.string.action_setting_title);
    }
}
