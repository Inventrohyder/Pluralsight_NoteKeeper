package com.inventrohyder.pluralsight_notekeeper;

/**
 * Created by Jim.
 */

public final class NoteInfo {
    private CourseInfo mCourse;
    private String mTitle;
    private String mText;
    private int mId;

    public NoteInfo(int id, CourseInfo course, String title, String text) {
        mId = id;
        mCourse = course;
        mTitle = title;
        mText = text;
    }

    public int getId() {
        return mId;
    }

    public CourseInfo getCourse() {
        return mCourse;
    }

    public void setCourse(CourseInfo course) {
        mCourse = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey() {
        return mCourse.getCourseId() + "|" + mTitle + "|" + mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

}
