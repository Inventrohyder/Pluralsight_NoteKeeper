package com.inventrohyder.courseEvents;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements CourseEventDisplayCallbacks {

    private ArrayList<String> mCourseEvents;
    private ArrayAdapter<String> mCourseEventsAdapter;
    private CourseEventReceiver mCourseEventReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCourseEvents = new ArrayList<>();
        mCourseEventsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCourseEvents);

        final ListView listView = findViewById(R.id.list_courses_events);
        listView.setAdapter(mCourseEventsAdapter);

        setupCourseEventsReceiver();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mCourseEventReceiver);
        super.onDestroy();
    }

    private void setupCourseEventsReceiver() {
        mCourseEventReceiver = new CourseEventReceiver();
        mCourseEventReceiver.setCourseEventDisplayCallbacks(this);

        // Register a receiver
        IntentFilter intentFilter = new IntentFilter(CourseEventReceiver.ACTION_COURSE_EVENT);
        registerReceiver(mCourseEventReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventReceived(String courseId, String courseMessage) {
        String displayText = courseId + ": " + courseMessage;
        mCourseEvents.add(displayText);
        mCourseEventsAdapter.notifyDataSetChanged();
    }
}