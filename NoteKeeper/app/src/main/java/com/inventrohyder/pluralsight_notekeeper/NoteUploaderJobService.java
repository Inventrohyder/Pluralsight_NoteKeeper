package com.inventrohyder.pluralsight_notekeeper;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;

public class NoteUploaderJobService extends JobService {

    public static final String EXTRA_DATA_URI = "com.inventrohyder.pluralsight_notekeeper.DATA_URI";
    private NoteUploader mNoteUploader;

    public NoteUploaderJobService() {
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String stringDataUri = jobParameters.getExtras().getString(EXTRA_DATA_URI);
                Uri dataUri = Uri.parse(stringDataUri);
                mNoteUploader.doUpload(dataUri);

                if (mNoteUploader.isNotCanceled())
                    jobFinished(jobParameters, false);

            }
        };

        mNoteUploader = new NoteUploader(this);

        new Thread(runnable).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mNoteUploader.cancel();
        return true;
    }
}
