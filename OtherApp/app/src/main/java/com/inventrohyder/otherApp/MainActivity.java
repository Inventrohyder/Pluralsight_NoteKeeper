package com.inventrohyder.otherApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_NOTEKEEPER_COURSES = 0;
    private SimpleCursorAdapter mCoursesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoursesAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2, null,
                new String[]{"course_title", "course_id"},
                new int[] {android.R.id.text1, android.R.id.text2}, 0);

        ListView listCourses = findViewById(R.id.list_courses);
        listCourses.setAdapter(mCoursesAdapter);

        LoaderManager.getInstance(this).initLoader(LOADER_NOTEKEEPER_COURSES, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri uri = Uri.parse("content://com.inventrohyder.noteKeeper.provider/courses");
        String [] columns = {"_id", "course_title", "course_id"};
        return new CursorLoader(this, uri, columns, null, null, "course_title");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCoursesAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCoursesAdapter.changeCursor(null);
    }
}