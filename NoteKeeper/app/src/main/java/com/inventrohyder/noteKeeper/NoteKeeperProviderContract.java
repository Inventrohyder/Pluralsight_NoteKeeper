package com.inventrohyder.noteKeeper;

import android.net.Uri;
import android.provider.BaseColumns;

final class NoteKeeperProviderContract {
    public static final String AUTHORITY = "com.inventrohyder.noteKeeper.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private NoteKeeperProviderContract() {
    }

    protected interface CoursesIdColumns {
        String COLUMN_COURSE_ID = "course_id";
    }

    protected interface CourseColumns {
        String COLUMN_COURSE_TITLE = "course_title";
    }

    protected interface NotesColumns {
        String COLUMN_NOTE_TITLE = "note_title";
        String COLUMN_NOTE_TEXT = "note_text";
    }

    public static final class Courses implements CourseColumns, BaseColumns, CoursesIdColumns {
        public static final String PATH = "courses";
        // content://com.inventrohyder.noteKeeper.provider/courses
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);
    }

    public static final class Notes implements NotesColumns, BaseColumns, CoursesIdColumns, CourseColumns {
        public static final String PATH = "notes";
        // content://com.inventrohyder.noteKeeper.provider/notes
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);

        public static final String PATH_EXPANDED = "notes_expanded";
        // content://com.inventrohyder.noteKeeper.provider/notes_expanded
        public static final Uri CONTENT_EXPANDED_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH_EXPANDED);
    }
}
