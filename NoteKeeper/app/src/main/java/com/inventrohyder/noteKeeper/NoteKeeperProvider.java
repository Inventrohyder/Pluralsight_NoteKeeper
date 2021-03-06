package com.inventrohyder.noteKeeper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import com.inventrohyder.noteKeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.inventrohyder.noteKeeper.NoteKeeperDatabaseContract.NoteInfoEntry;
import com.inventrohyder.noteKeeper.NoteKeeperProviderContract.Courses;
import com.inventrohyder.noteKeeper.NoteKeeperProviderContract.CoursesIdColumns;
import com.inventrohyder.noteKeeper.NoteKeeperProviderContract.Notes;

public class NoteKeeperProvider extends ContentProvider {

    public static final String MIME_VENDOR_TYPE = "vnd." + NoteKeeperProviderContract.AUTHORITY + ".";
    private NoteKeeperOpenHelper mDbOpenHelper;

    public static final int COURSES = 0;
    public static final int NOTES = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int NOTES_EXPANDED = 2;

    public static final int NOTES_ROW = 3;

    static {
        sUriMatcher.addURI(NoteKeeperProviderContract.AUTHORITY, Courses.PATH, COURSES);
        sUriMatcher.addURI(NoteKeeperProviderContract.AUTHORITY, Notes.PATH, NOTES);
        sUriMatcher.addURI(NoteKeeperProviderContract.AUTHORITY, Notes.PATH_EXPANDED, NOTES_EXPANDED);
        sUriMatcher.addURI(NoteKeeperProviderContract.AUTHORITY, Notes.PATH + "/#", NOTES_ROW);
    }

    public NoteKeeperProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        int uriMatch = sUriMatcher.match(uri);

        int deletedRows = 0;

        switch (uriMatch) {
            case COURSES:
                throw new UnsupportedOperationException("The courses table does not support deletion");
            case NOTES_ROW:
                long rowId = ContentUris.parseId(uri);
                String rowSelection = NoteInfoEntry._ID + " = ?";
                String[] rowSelectionArgs = {Long.toString(rowId)};
                deletedRows = db.delete(NoteInfoEntry.TABLE_NAME, rowSelection, rowSelectionArgs);
        }

        return deletedRows;

    }

    @Override
    public String getType(@NonNull Uri uri) {
        String mimeType = null;

        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case COURSES:
                // vnd.android.cursor.dir/vnd.com.inventrohyder.noteKeeper.courses
                mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Courses.PATH;
                break;
            case NOTES:
                // vnd.android.cursor.dir/vnd.com.inventrohyder.noteKeeper.notes
                mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Notes.PATH;
                break;
            case NOTES_EXPANDED:
                // vnd.android.cursor.dir/vnd.com.inventrohyder.noteKeeper.notes_expanded
                mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Notes.PATH_EXPANDED;
                break;
            case NOTES_ROW:
                // vnd.android.cursor.dir/vnd.com.inventrohyder.noteKeeper.notes
                mimeType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Notes.PATH;
                break;
        }

        return mimeType;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        long rowId;
        Uri rowUri = null;
        int uriMatch = sUriMatcher.match(uri);

        switch (uriMatch) {
            case NOTES:
                rowId = db.insert(NoteInfoEntry.TABLE_NAME, null, values);

                // content://com.inventrohyder.noteKeeper/notes/1
                rowUri = ContentUris.withAppendedId(Notes.CONTENT_URI, rowId);
                break;
            case COURSES:
                rowId = db.insert(CourseInfoEntry.TABLE_NAME, null, values);

                // content://com.inventrohyder.noteKeeper/courses/1
                rowUri = ContentUris.withAppendedId(Courses.CONTENT_URI, rowId);
                break;
            case NOTES_EXPANDED:
                // throw exception saying that this is a read-only table
                throw new UnsupportedOperationException("The expanded notes table is READ ONLY");
        }

        return rowUri;
    }

    @Override
    public boolean onCreate() {
        mDbOpenHelper = new NoteKeeperOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case COURSES:
                cursor = db.query(CourseInfoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case NOTES:
                cursor = db.query(NoteInfoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case NOTES_EXPANDED:
                cursor = notesExpandedQuery(db, projection, selection, selectionArgs, sortOrder);
                break;
            case NOTES_ROW:
                long rowId = ContentUris.parseId(uri);
                String rowSelection = NoteInfoEntry._ID + " = ?";
                String[] rowSelectionArgs = {Long.toString(rowId)};
                cursor = db.query(NoteInfoEntry.TABLE_NAME, projection, rowSelection,
                        rowSelectionArgs, null, null, null);
                break;
        }

        return cursor;
    }

    private Cursor notesExpandedQuery(SQLiteDatabase db, String[] projection, String selection,
                                      String[] selectionArgs, String sortOrder) {

        String[] columns = new String[projection.length];
        for (int idx = 0; idx < projection.length; idx++) {
            columns[idx] = (projection[idx].equals(BaseColumns._ID) ||
                    projection[idx].equals(CoursesIdColumns.COLUMN_COURSE_ID)) ?
                    NoteInfoEntry.getQName(projection[idx]) : projection[idx];
        }

        String tablesWithJoin = NoteInfoEntry.TABLE_NAME + " JOIN " +
                CourseInfoEntry.TABLE_NAME + " ON " +
                NoteInfoEntry.getQName(NoteInfoEntry.COLUMN_COURSE_ID) + " = " +
                CourseInfoEntry.getQName(CourseInfoEntry.COLUMN_COURSE_ID);

        return db.query(tablesWithJoin, columns, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        int uriMatch = sUriMatcher.match(uri);

        int updatedRows = 0;

        switch (uriMatch) {
            case COURSES:
                throw new UnsupportedOperationException("The courses table does not support update");
            case NOTES_ROW:
                long rowId = ContentUris.parseId(uri);
                String rowSelection = NoteInfoEntry._ID + " = ?";
                String[] rowSelectionArgs = {Long.toString(rowId)};
                updatedRows = db.update(NoteInfoEntry.TABLE_NAME, values, rowSelection, rowSelectionArgs);
        }

        return updatedRows;
    }
}
