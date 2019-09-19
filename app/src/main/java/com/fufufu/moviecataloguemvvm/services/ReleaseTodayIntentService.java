package com.fufufu.moviecataloguemvvm.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class ReleaseTodayIntentService extends IntentService {
    private final int ID_REMINDER = 957;
    private Repository repository;

    public ReleaseTodayIntentService() {
        super("ReleaseTodayIntentService");
        repository = new Repository();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d("Film Size", String.valueOf(repository.getReleaseFilmToday("en", "2019-09-16", "2019-09-16").size()));
            List<Film> releaseFilmTodayResult = repository.getReleaseFilmToday("en", "2019-09-16", "2019-09-16");
            for (int i = 0; i< releaseFilmTodayResult.size(); i++){
                showReminderNotification(getApplicationContext(), releaseFilmTodayResult.get(i).getTitle(), releaseFilmTodayResult.get(i).getOverview(), releaseFilmTodayResult.get(i).getPosterPath(), ID_REMINDER);
            }
            if(releaseFilmTodayResult.size() != 0){
                Log.d("releaseTodayISSize", String.valueOf(releaseFilmTodayResult.size()));
            }
            else {
                Log.d("ReleaseToday", "Null");
            }
        }
    }

    private void showReminderNotification(Context context, String title, String message, String imageUrl, int notifId) {
        String CHANNEL_ID = "Channel_releaseTodayReminder_1";
        String CHANNEL_NAME = "ReleaseTodayReminderChannel";

        try {
            InputStream inputStreamImage = new URL("https://image.tmdb.org/t/p/w500"+imageUrl).openStream();
            Bitmap bmp = BitmapFactory.decodeStream(inputStreamImage);
            NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setLargeIcon(bmp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

                builder.setChannelId(CHANNEL_ID);

                if (notificationManagerCompat != null) {
                    notificationManagerCompat.createNotificationChannel(channel);
                }
            }

            Notification notification = builder.build();

            if (notificationManagerCompat != null) {
                notificationManagerCompat.notify(notifId, notification);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
