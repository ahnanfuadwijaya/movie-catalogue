package com.fufufu.moviecatalogue.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

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
import java.util.ArrayList;


public class ReleaseTodayIntentService extends IntentService {
    private int GROUP_ID = 9596;
    private Repository repository;
    private NotificationManager notificationManager;
    private final String CHANNEL_ID = "Channel_releaseTodayReminder_1";
    private final String CHANNEL_NAME = "ReleaseTodayReminderChannel";
    private final static String GROUP_KEY_FILMS = "com.fufufu.FILMS";

    public ReleaseTodayIntentService() {
        super("ReleaseTodayIntentService");
        repository = new Repository();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            repository.setReleaseFilmToday("en", "2019-09-16", "2019-09-16");

            Log.d("Film Size", String.valueOf(repository.getReleaseFilmToday().size()));

            ArrayList<Film> releaseFilmTodayResult = repository.getReleaseFilmToday();

            if(releaseFilmTodayResult != null){
                showReminderNotification(releaseFilmTodayResult);
            }


            if(releaseFilmTodayResult != null){
                Log.d("releaseTodayISSize", String.valueOf(releaseFilmTodayResult.size()));
                Log.d("Title Pertama", releaseFilmTodayResult.get(0).getTitle());
                Log.d("Title Terakhir", releaseFilmTodayResult.get(releaseFilmTodayResult.size()-1).getTitle());
            }
            else {
                Log.d("ReleaseTodayIS", "Null");
            }
        }
    }

    private void showReminderNotification(ArrayList<Film> films) {

        try {

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            NotificationCompat.Builder groupBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

                for(int i = 0; i < films.size(); i++){

                    Intent intent = new Intent(this, DetailFilmActivity.class);
                    intent.putExtra("filmId", films.get(i).getId());
                    intent.setAction(String.valueOf(films.get(i).getId()));

                    //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DetailFilmActivity.class), 0);
                    PendingIntent contentIntent = PendingIntent.getActivity(this, (int)films.get(i).getId(), intent, PendingIntent.FLAG_ONE_SHOT);

                    Log.d("Path", films.get(i).getPosterPath());

                    URL url = new URL(films.get(i).getPosterPath());
                    URLConnection connection = url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    Bitmap bmp = BitmapFactory.decodeStream(in);
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    bigPictureStyle.setBigContentTitle(films.get(i).getTitle()).setSummaryText(films.get(i).getOverview()).bigPicture(bmp).bigLargeIcon(bmp);

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
                    notificationManager.notify((int)films.get(i).getId(), notification);
                }

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            bigPictureStyle.setSummaryText("Release Today").setBigContentTitle("Release Today");

            groupBuilder.setGroup(GROUP_KEY_FILMS)
                    .setGroupSummary(true)
                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                    .setStyle(bigPictureStyle)
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
            notificationManager.notify(GROUP_ID, groupNotification);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
