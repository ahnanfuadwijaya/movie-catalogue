package com.fufufu.moviecataloguemvvm.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;

import java.util.List;

public class ReleaseTodayReminder extends JobScheduler {
    //endpoint
    //https://api.themoviedb.org/3/discover/movie?api_key=<<api_key>>&primary_release_date.gte=2019-09-14&primary_release_date.lte=2019-09-14

    @Override
    public int schedule(JobInfo jobInfo) {
        return 0;
    }

    @Override
    public int enqueue(JobInfo jobInfo, JobWorkItem jobWorkItem) {
        return 0;
    }

    @Override
    public void cancel(int i) {

    }

    @Override
    public void cancelAll() {

    }

    @Override
    public List<JobInfo> getAllPendingJobs() {
        return null;
    }

    @Override
    public JobInfo getPendingJob(int i) {
        return null;
    }
}
