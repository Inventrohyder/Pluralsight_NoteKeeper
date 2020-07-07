package com.inventrohyder.pluralsight_notekeeper;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class NoteUploaderJobService extends JobService {

    public static final String EXTRA_DATA_URI = "com.inventrohyder.pluralsight_notekeeper.DATA_URI";

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
