package com.fufufu.moviecataloguemvvm.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.fufufu.moviecataloguemvvm.R;
import com.fufufu.moviecataloguemvvm.models.Film;
import com.fufufu.moviecataloguemvvm.models.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReleaseTodayReminderJS extends JobService {
    //endpoint
    //https://api.themoviedb.org/3/discover/movie?api_key=<<api_key>>&primary_release_date.gte=2019-09-14&primary_release_date.lte=2019-09-14
    public static final String TAG = ReleaseTodayReminderJS.class.getSimpleName();
    private final int notifId = 234;

    private void getReleaseTodayFilms(final JobParameters jobParameters) {
        try{
            releaseTodayFilms = repository.getReleaseFilmToday("en", "2019-09-16", "2019-09-16");
            if(releaseTodayFilms != null){
                for(int i=0; i< releaseTodayFilms.size(); i++){
                    Log.d("releaseToday.size()", String.valueOf(releaseTodayFilms.size()));
                    showNotification(getApplicationContext(), releaseTodayFilms.get(i).getTitle(), releaseTodayFilms.get(i).getOverview(), notifId);
                }
            }
            if(Objects.requireNonNull(releaseTodayFilms).size() != 0){
                jobFinished(jobParameters, false);
                Log.d("Title", releaseTodayFilms.get(0).getTitle());
            }
            else{
                jobFinished(jobParameters, true);
                Log.d("releaseToday.size()", String.valueOf(releaseTodayFilms.size()));
                Log.d("Reschedule", "true");
            }

        } catch (Exception e){
            Log.d("Exception", e.getMessage());
            jobFinished(jobParameters, true);
        }
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_Release_Today_Reminder_1";
        String CHANNEL_NAME = "Job scheduler channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_favorite_24px)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
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
    }

    private List<Film> releaseTodayFilms = new ArrayList<>();
    private Repository repository = new Repository();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("onStartJob", "executed");
        getReleaseTodayFilms(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("onStopJob", "executed");
        return true;
    }


}
