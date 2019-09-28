package com.fufufu.moviecatalogue.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.fufufu.moviecatalogue.R;
import com.fufufu.moviecatalogue.models.Film;
import com.fufufu.moviecatalogue.models.Repository;
import com.fufufu.moviecatalogue.views.DetailFilmActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReleaseTodayIntentService extends IntentService {
    private Repository repository;
    private final static String GROUP_KEY_FILMS = "com.fufufu.FILMS";

    public ReleaseTodayIntentService() {
        super("ReleaseTodayIntentService");
        repository = new Repository();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String langPref = "Language";
            SharedPreferences prefs = getSharedPreferences("CommonPrefs", AppCompatActivity.MODE_PRIVATE);
            String language = prefs.getString(langPref, "");
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            repository.setReleaseFilmToday(language, date, date);
            ArrayList<Film> releaseFilmTodayResult = repository.getReleaseFilmToday();
            if(releaseFilmTodayResult.size() != 0){
                showReminderNotification(releaseFilmTodayResult);
            }
        }
    }

    private void showReminderNotification(ArrayList<Film> films) {
        try {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            String CHANNEL_ID = "Channel_releaseTodayReminder_1";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            NotificationCompat.Builder groupBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String CHANNEL_NAME = "ReleaseTodayReminderChannel";
            for(int i = 0; i < films.size(); i++){
                    Intent intent = new Intent(this, DetailFilmActivity.class);
                    intent.putExtra("filmId", films.get(i).getId());
                    intent.setAction(String.valueOf(films.get(i).getId()));
                    PendingIntent contentIntent = PendingIntent.getActivity(this, (int)films.get(i).getId(), intent, PendingIntent.FLAG_ONE_SHOT);
                    URL url = new URL(films.get(i).getPosterPath());
                    URLConnection connection = url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    Bitmap bmp = BitmapFactory.decodeStream(in);
                    bigPictureStyle.setBigContentTitle(films.get(i).getTitle()).bigPicture(bmp).bigLargeIcon(bmp);
                    builder.setGroup(GROUP_KEY_FILMS)
                            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                            .setLargeIcon(bmp)
                            .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                            .setStyle(bigPictureStyle)
                            .setContentTitle(films.get(i).getTitle())
                            .setContentText(films.get(i).getOverview())
                            .setVibrate(new long[]{1000})
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(contentIntent);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                CHANNEL_NAME,
                                NotificationManager.IMPORTANCE_LOW);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[]{1000});
                        notificationManager.createNotificationChannel(channel);
                    }

                    Notification notification = builder.build();
                    long id = films.get(i).getId();
                    notificationManager.notify((int) id, notification);
                }

                if(films.size() > 1){
                    bigPictureStyle.setSummaryText("Release Today").setBigContentTitle("Release Today");
                    groupBuilder.setGroup(GROUP_KEY_FILMS)
                            .setGroupSummary(true)
                            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                            .setStyle(bigPictureStyle)
                            .setContentTitle("Release Today")
                            .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                            .setVibrate(new long[]{1000})
                            .setSound(alarmSound)
                            .setAutoCancel(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                CHANNEL_NAME,
                                NotificationManager.IMPORTANCE_LOW);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[]{1000});
                        notificationManager.createNotificationChannel(channel);
                    }

                    Notification groupNotification = groupBuilder.build();
                    int GROUP_ID = 9596;
                    notificationManager.notify(GROUP_ID, groupNotification);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
