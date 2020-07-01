package com.inventrohyder.pluralsight_notekeeper;

import androidx.annotation.NonNull;

/**
 * Created by Jim.
 */

public final class CourseInfo {
    private final String mCourseId;
    private final String mTitle;

    public CourseInfo(String courseId, String title) {
        mCourseId = courseId;
        mTitle = title;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

    @NonNull
    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseInfo that = (CourseInfo) o;

        return mCourseId.equals(that.mCourseId);

    }

    @Override
    public int hashCode() {
        return mCourseId.hashCode();
    }
}
