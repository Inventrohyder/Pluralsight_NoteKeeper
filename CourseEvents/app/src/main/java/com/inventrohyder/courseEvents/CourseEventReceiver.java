package com.inventrohyder.courseEvents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CourseEventReceiver extends BroadcastReceiver {

    public static final String ACTION_COURSE_EVENT = "com.inventrohyder.noteKeeper.COURSE_EVENT";

    public static final String EXTRA_COURSE_ID = "com.inventrohyder.noteKeeper.COURSE_ID";
    public static final String EXTRA_COURSE_MESSAGE = "com.inventrohyder.noteKeeper.COURSE_MESSAGE";

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
