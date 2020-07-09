package com.inventrohyder.pluralsight_notekeeper;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class NoteBackupService extends IntentService {

    public static final String EXTRA_COURSE_ID = "com.inventrohyder.pluralsight_notekeeper.extra.COURSE_ID";

    public NoteBackupService() {
        super("NoteBackupService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String backupCourseId = intent.getStringExtra(EXTRA_COURSE_ID);
            assert backupCourseId != null;
            NoteBackup.doBackup(this, backupCourseId);
        }
    }
}