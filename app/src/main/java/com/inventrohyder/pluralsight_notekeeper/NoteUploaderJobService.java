package com.inventrohyder.pluralsight_notekeeper;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class NoteUploaderJobService extends JobService {
    public NoteUploaderJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
