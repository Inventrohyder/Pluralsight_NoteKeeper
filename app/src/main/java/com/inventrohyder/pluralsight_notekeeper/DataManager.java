package com.inventrohyder.pluralsight_notekeeper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.inventrohyder.pluralsight_notekeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.inventrohyder.pluralsight_notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim.
 */

public class DataManager {
    private static DataManager ourInstance = null;

    private final List<CourseInfo> mCourses = new ArrayList<>();
    private final List<NoteInfo> mNotes = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(NoteKeeperOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query the courses
        final String[] courseColumns = {
                CourseInfoEntry.COLUMN_COURSE_ID,
                CourseInfoEntry.COLUMN_COURSE_TITLE
        };

        // Load the courses ordered by the course title in descending order
        final Cursor courserCursor = db.query(CourseInfoEntry.TABLE_NAME, courseColumns,
                null, null, null, null, CourseInfoEntry.COLUMN_COURSE_TITLE + " DESC");
        loadCoursesFromDatabase(courserCursor);

        // Query the notes
        String[] noteColumns = {
                NoteInfoEntry.COLUMN_NOTE_TITLE,
                NoteInfoEntry.COLUMN_NOTE_TEXT,
                NoteInfoEntry.COLUMN_COURSE_ID,
                NoteInfoEntry._ID
        };

        // Load the notes ordered by both the courseId and NoteTitle
        String noteOrderBy = NoteInfoEntry.COLUMN_COURSE_ID + ", " + NoteInfoEntry.COLUMN_NOTE_TITLE;
        final Cursor noteCursor = db.query(NoteInfoEntry.TABLE_NAME, noteColumns,
                null, null, null, null, noteOrderBy);
        loadNotesFromDatabase(noteCursor);
    }

    private static void loadNotesFromDatabase(Cursor cursor) {
        // Get the desired column positions
        int noteTitlePos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TEXT);
        int courseIdPos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);

        DataManager dm = getInstance();
        dm.mNotes.clear();

        while (cursor.moveToNext()) {
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);
            String courseId = cursor.getString(courseIdPos);

            // Access the appropriate course
            CourseInfo noteCourse = dm.getCourse(courseId);

            NoteInfo note = new NoteInfo(noteCourse, noteTitle, noteText);
            dm.mNotes.add(note);
        }

        // Free up resources by closing the cursor
        cursor.close();

    }

    private static void loadCoursesFromDatabase(Cursor cursor) {
        // Get the desired column positions
        int courseIdPos = cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_ID);
        int courseTitlePos = cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_TITLE);

        DataManager dm = getInstance();
        dm.mCourses.clear();

        while (cursor.moveToNext()) {
            String courseId = cursor.getString(courseIdPos);
            String courseTitle = cursor.getString(courseTitlePos);
            CourseInfo course = new CourseInfo(courseId, courseTitle);

            dm.mCourses.add(course);
        }

        // Free up resources by closing the cursor
        cursor.close();
    }

    public List<NoteInfo> getNotes() {
        return mNotes;
    }

    public int createNewNote() {
        NoteInfo note = new NoteInfo(null, null, null);
        mNotes.add(note);
        return mNotes.size() - 1;
    }

    public int findNote(NoteInfo note) {
        for (int index = 0; index < mNotes.size(); index++) {
            if (note.equals(mNotes.get(index)))
                return index;
        }

        return -1;
    }

    public List<CourseInfo> getCourses() {
        return mCourses;
    }

    public CourseInfo getCourse(String id) {
        for (CourseInfo course : mCourses) {
            if (id.equals(course.getCourseId()))
                return course;
        }
        return null;
    }

}
