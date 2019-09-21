package com.fufufu.moviecataloguemvvm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    private final int ID_REMINDER = 957;
    private String releaseTodayPref = "ReleaseToday";
    private String dailyReminderPref = "DailyReminder";
    private boolean dailyReminderState = false;
    private boolean releaseTodayState = false;

    private void loadPreference(){
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        dailyReminderState = prefs.getBoolean(dailyReminderPref, false);
        releaseTodayState = prefs.getBoolean(releaseTodayPref, false);
    }

    private void savePreferenceReleaseToday(boolean b){
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(releaseTodayPref, b);
        editor.apply();
    }

    private void savePreferenceDailyRemainder(boolean b){
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(dailyReminderPref, b);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding activitySettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        loadPreference();
        activitySettingBinding.swDailyReminder.setChecked(dailyReminderState);
        activitySettingBinding.swReleaseTodayReminder.setChecked(releaseTodayState);
        final DailyReminder dailyReminder = new DailyReminder();
        activitySettingBinding.swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    savePreferenceDailyRemainder(true);
                    dailyReminder.setReminder(getApplicationContext(), "Check out latest movies and tv shows today");
                    Toast.makeText(getApplicationContext(), "Daily Reminder: On", Toast.LENGTH_LONG).show();
                }
                else {
                    savePreferenceDailyRemainder(false);
                    dailyReminder.cancelReminder(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Daily Reminder: Off", Toast.LENGTH_LONG).show();
                }
            }
        });


        activitySettingBinding.swReleaseTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    savePreferenceReleaseToday(true);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 8);
                    calendar.set(Calendar.MINUTE, 0);
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
                    savePreferenceReleaseToday(false);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), ReleaseTodayIntentService.class);
                    PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), ID_REMINDER, intent, 0);
                    pendingIntent.cancel();

                    if (alarmManager != null) {
                        alarmManager.cancel(pendingIntent);
                    }

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
