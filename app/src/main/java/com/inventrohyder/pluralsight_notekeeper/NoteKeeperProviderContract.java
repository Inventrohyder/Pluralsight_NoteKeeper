package com.inventrohyder.pluralsight_notekeeper;

import android.net.Uri;

final class NoteKeeperProviderContract {
    public static final String AUTHORITY = "com.inventrohyder.pluralsight_notekeeper.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private NoteKeeperProviderContract() {
    }

    public static final class Courses {
        public static final String PATH = "courses";
        // content://com.inventrohyder.pluralsight_notekeeper.provider/courses
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);
    }

    public static final class Notes {
        public static final String PATH = "notes";
        // content://com.inventrohyder.pluralsight_notekeeper.provider/notes
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);
    }
}
