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

                if (!mNoteUploader.isCanceled())
                    jobFinished(jobParameters, false);

            }
        };

//        AsyncTask<JobParameters, Void, Void> task = new AsyncTask<JobParameters, Void, Void>() {
//            @Override
//            protected Void doInBackground(JobParameters... backgroundParams) {
//                JobParameters jobParams = backgroundParams[0];
//
//                String stringDataUri = jobParams.getExtras().getString(EXTRA_DATA_URI);
//                Uri dataUri = Uri.parse(stringDataUri);
//                mNoteUploader.doUpload(dataUri);
//
//                if (!mNoteUploader.isCanceled())
//                    jobFinished(jobParams, false);
//
//                return null;
//            }
//        };

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
