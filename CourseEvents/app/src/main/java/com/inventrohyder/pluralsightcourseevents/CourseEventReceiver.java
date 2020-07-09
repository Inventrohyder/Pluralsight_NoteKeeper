package com.inventrohyder.pluralsightcourseevents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CourseEventReceiver extends BroadcastReceiver {

    public static final String ACTION_COURSE_EVENT = "com.inventrohyder.pluralsight_notekeeper.COURSE_EVENT";

    public static final String EXTRA_COURSE_ID = "com.inventrohyder.pluralsight_notekeeper.COURSE_ID";
    public static final String EXTRA_COURSE_MESSAGE = "com.inventrohyder.pluralsight_notekeeper.COURSE_MESSAGE";

    private CourseEventDisplayCallbacks mCourseEventDisplayCallbacks;

    public void setCourseEventDisplayCallbacks(CourseEventDisplayCallbacks courseEventDisplayCallbacks) {
        mCourseEventDisplayCallbacks = courseEventDisplayCallbacks;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_COURSE_EVENT.equals(intent.getAction())) {
            String courseId = intent.getStringExtra(EXTRA_COURSE_ID);
            String courseMessage = intent.getStringExtra(EXTRA_COURSE_MESSAGE);

            if (mCourseEventDisplayCallbacks != null) {
                mCourseEventDisplayCallbacks.onEventReceived(courseId, courseMessage);
            }
        }
    }
}
