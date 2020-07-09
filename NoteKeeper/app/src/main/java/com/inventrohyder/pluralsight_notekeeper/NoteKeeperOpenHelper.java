package com.inventrohyder.pluralsight_notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.inventrohyder.pluralsight_notekeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.inventrohyder.pluralsight_notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;

class NoteKeeperOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NoteKeeper.db";
    public static final int DATABASE_VERSION = 2;

    public NoteKeeperOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the database tables
        sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_TABLE);
        // Create the indexes
        sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_INDEX);
        sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_INDEX);

        // Insert Initial data
        DatabaseDataWorker worker = new DatabaseDataWorker(sqLiteDatabase);
        worker.insertCourses();
        worker.insertSampleNotes();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i < 2) {
            // Create the indexes
            sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_INDEX);
            sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_INDEX);
        }
    }
}
