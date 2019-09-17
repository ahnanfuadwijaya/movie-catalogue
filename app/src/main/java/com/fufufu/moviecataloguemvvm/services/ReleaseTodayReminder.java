package com.fufufu.moviecataloguemvvm.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReleaseTodayReminder extends BroadcastReceiver {
    private final int ID_REMINDER = 957;
    private Repository repository = new Repository();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ReleaseToday", "onReceive");
        List<Film> releaseFilmTodayResult = new ArrayList<>();
        try {
            releaseFilmTodayResult = new GetReleaseTodayAsync().execute().get();
            Log.d("releaseTodaySize", String.valueOf(releaseFilmTodayResult.size()));
            for (int i = 0; i< releaseFilmTodayResult.size(); i++){
                showReminderNotification(context, releaseFilmTodayResult.get(i).getTitle(), releaseFilmTodayResult.get(i).getOverview(), releaseFilmTodayResult.get(i).getPosterPath(), ID_REMINDER);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(releaseFilmTodayResult.size() != 0){
            Log.d("releaseTodaySize", String.valueOf(releaseFilmTodayResult.size()));
        }
        else {
            Log.d("ReleaseToday", "Null");
        }
    }
    public void setReminder(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminder.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);

        Log.d("Calendar", calendar.getTime().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("ReleaseTodayReminder", "Set");
        }
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
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

    private static class GetReleaseTodayAsync extends AsyncTask<Void, Void, List<Film>>{
        Repository repository = new Repository();
        @Override
        protected List<Film> doInBackground(Void... voids) {
            return repository.getReleaseFilmToday("en", "2019-09-16", "2019-09-16");
        }
    }
}
