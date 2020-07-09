package com.inventrohyder.noteKeeper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {
    static DataManager sDataManager;
    @BeforeClass
    public static void classSetUp(){
        sDataManager = DataManager.getInstance();
    }
    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote() {
        final CourseInfo course = sDataManager.getCourse("java_lang");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of our test note";

        // Click the create new note Floating Action button
        onView(withId(R.id.fab)).perform(click());

        // Click on the spinner first
        onView(withId(R.id.spinner_courses)).perform(click());

        // Choose the course that we want based on the data in the spinner
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());

        // Check if the course title to select matches the title we want
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString(course.getTitle()))));

        // Input the note title and check if it is correctly input
        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));

        // Input the note text and close the keyboard
        onView(withId(R.id.text_note_text)).perform(typeText(noteText),
                closeSoftKeyboard());

        // Check if the note text was correctly input
        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(noteText))));

        // Go back to the MainActivity
        pressBack();

        // After the user leaves the activity the note should be saved
        int noteIndex = sDataManager.getNotes().size() - 1;
        NoteInfo note = sDataManager.getNotes().get(noteIndex);
        assertEquals(course, note.getCourse());
        assertEquals(noteTitle, note.getTitle());
        assertEquals(noteText, note.getText());
    }



}
