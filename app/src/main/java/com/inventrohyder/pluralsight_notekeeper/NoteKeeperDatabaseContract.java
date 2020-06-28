package com.inventrohyder.pluralsight_notekeeper;

public final class NoteKeeperDatabaseContract {
    private NoteKeeperDatabaseContract() {
    }  // Make non-creatable


    // Classes for the Tables
    // course_info table
    public static final class CourseInfoEntry {
        public static final String TABLE_NAME = "course_info";
        public static final String COLUMN_COURSE_ID = "course_id";
        public static final String COLUMN_COURSE_TITILE = "course_title";

        // CREATE TABLE course_info (course_id, course_title)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_COURSE_ID + ", " +
                        COLUMN_COURSE_TITILE + ")";
    }

    // note_info table
    public static final class NoteInfoEntry {
        public static final String TABLE_NAME = "note_info";
        public static final String COLUMN_NOTE_ID = "note_id";
        public static final String COLUMN_NOTE_TITLE = "note_title";
        public static final String COLUMN_NOTE_TEXT = "note_text";
        public static final String COLUMN_COURSE_ID = "course_id";

        // CREATE TABLE note_info (note_id, note_title, note_text, course_id)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NOTE_ID + ", " +
                        COLUMN_NOTE_TITLE + ", " +
                        COLUMN_NOTE_TEXT + ", " +
                        COLUMN_COURSE_ID + ")";
    }
}
