package com.fufufu.moviecataloguemvvm.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;
import com.fufufu.moviecataloguemvvm.views.DetailFilmActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ReleaseTodayIntentService extends IntentService {
    //private final int ID_REMINDER = 957;
    private Repository repository;
    private NotificationManager notificationManager;
    private final String CHANNEL_ID = "Channel_releaseTodayReminder_1";
    private final String CHANNEL_NAME = "ReleaseTodayReminderChannel";
    private final static String GROUP_KEY_FILMS = "group_key_emails";

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

            for (int i = 0; i< releaseFilmTodayResult.size(); i++){
                showReminderNotification(getApplicationContext(), releaseFilmTodayResult.get(i).getId(), releaseFilmTodayResult.get(i).getTitle(), releaseFilmTodayResult.get(i).getOverview(), releaseFilmTodayResult.get(i).getPosterPath());
            }
            if(releaseFilmTodayResult.size() != 0){
                Log.d("releaseTodayISSize", String.valueOf(releaseFilmTodayResult.size()));
                Log.d("Title Pertama", releaseFilmTodayResult.get(0).getTitle());
                Log.d("Title Terakhir", releaseFilmTodayResult.get(releaseFilmTodayResult.size()-1).getTitle());
            }
            else {
                Log.d("ReleaseTodayIS", "Null");
            }
        }
    }

    private void showReminderNotification(Context context, int idFilm, String title, String message, String imageUrl) {

        try {

            notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(this, DetailFilmActivity.class);
            intent.putExtra("filmId", idFilm);

            //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DetailFilmActivity.class), 0);
            PendingIntent contentIntent = PendingIntent.getActivity(this, idFilm, intent, PendingIntent.FLAG_ONE_SHOT);

            Log.d("Path", imageUrl);

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream in = connection.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(in);
            //NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_favorite_24px)
                    .setLargeIcon(bmp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000})
                    .setSound(alarmSound)
                    .setGroup(GROUP_KEY_FILMS)
                    .setGroupSummary(true)
                    .setAutoCancel(true);

            builder.setContentIntent(contentIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{1000});

                builder.setChannelId(CHANNEL_ID);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            Notification notification = builder.build();

            if (notificationManager != null) {
                notificationManager.notify(idFilm, notification);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
