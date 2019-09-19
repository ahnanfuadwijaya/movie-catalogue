package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.databinding.ActivitySettingBinding;
import com.fufufu.moviecataloguemvvm.services.DailyReminder;
import com.fufufu.moviecataloguemvvm.services.ReleaseTodayIntentService;
import com.fufufu.moviecataloguemvvm.services.ReleaseTodayReminder;
import com.fufufu.moviecataloguemvvm.services.ReleaseTodayReminderJS;
import com.fufufu.moviecataloguemvvm.services.ReleaseTodayReminderService;

import java.util.Calendar;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private final int ID_REMINDER = 957;

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

        final ReleaseTodayReminder releaseTodayReminder = new ReleaseTodayReminder();
        activitySettingBinding.swReleaseTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //releaseTodayReminder.setReminder(getApplicationContext());

                    String releaseTodayPref = "ReleaseToday";
                    SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(releaseTodayPref, true);
                    editor.apply();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 1);
                    calendar.set(Calendar.SECOND, 0);


                    Intent intent = new Intent(getApplicationContext(), ReleaseTodayIntentService.class);
                    PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), ID_REMINDER, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }

                    Toast.makeText(getApplicationContext(), "Release Today Reminder: On", Toast.LENGTH_LONG).show();
                }
                else{
                    releaseTodayReminder.cancelReminder(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Release Today Reminder: Off", Toast.LENGTH_LONG).show();
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
