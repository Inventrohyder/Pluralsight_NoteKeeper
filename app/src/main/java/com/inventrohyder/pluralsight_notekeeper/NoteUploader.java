package com.inventrohyder.pluralsight_notekeeper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.inventrohyder.pluralsight_notekeeper.NoteKeeperProviderContract.Notes;

class NoteUploader {
    private final String TAG = getClass().getSimpleName();

    private final Context mContext;
    private boolean mCanceled;

    public NoteUploader(Context context) {
        mContext = context;
    }

    private static void simulateLongRunningWork() {
        try {
            Thread.sleep(2000); // Pause execution for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public void cancel() {
        mCanceled = true;
    }

    public void doUpload(Uri dataUri) {
        String[] columns = {
                Notes.COLUMN_COURSE_ID,
                Notes.COLUMN_NOTE_TITLE,
                Notes.COLUMN_NOTE_TEXT
        };

        Cursor cursor = mContext.getContentResolver().query(dataUri, columns, null, null, null);
        int courseIdPos = cursor.getColumnIndex(Notes.COLUMN_COURSE_ID);
        int noteTitlePos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TEXT);

        Log.i(TAG, ">>>*** UPLOAD START - " + dataUri + " ***<<<");
        mCanceled = false;
        while (!isCanceled() && cursor.moveToNext()) {
            String courseId = cursor.getString(courseIdPos);
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);

            if (!noteTitle.equals("")) {
                Log.i(TAG, ">>>Uploading Note<<< " + courseId + "|" + noteTitle + "|" + noteText);
                simulateLongRunningWork();
            }
        }
    }

}
