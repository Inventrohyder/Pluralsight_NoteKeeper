package com.inventrohyder.noteKeeper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.inventrohyder.noteKeeper.NoteKeeperProviderContract.Courses;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private Cursor mCursor;
    private final LayoutInflater mLayoutInflater;
    private int mCoursePos;

    public CourseRecyclerAdapter(Context context, Cursor cursor) {
        mCursor = cursor;
        mLayoutInflater = LayoutInflater.from(context);
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (mCursor == null)
            return;
        // Get column indexes from mCursor
        mCoursePos = mCursor.getColumnIndex(Courses.COLUMN_COURSE_TITLE);
    }

    public void changeCursor(Cursor cursor) {
        if (mCursor != null && mCursor != cursor) {
            mCursor.close();
        }
        mCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mLayoutInflater.inflate(R.layout.item_course_list, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        mCursor.moveToPosition(i);
        String courseTitle = mCursor.getString(mCoursePos);
        viewHolder.mTextCourse.setText(courseTitle);
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mTextCourse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextCourse = itemView.findViewById(R.id.text_course);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(
                            v,
                            mCursor.getString(mCoursePos),
                            Snackbar.LENGTH_LONG
                    ).show();
                }
            });
        }
    }
}
