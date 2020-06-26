package com.inventrohyder.pluralsight_notekeeper;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.*;

public class NextThroughNotesTest {
    // Our ActivityTestRule to take care of setting up our target MainActivity
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void NextThroughNotes() {
        // Using the DrawerActions to perform actions on the DrawerLayout
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        // Select the notes NavigationView in the draw to select the notes
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes));

        // Select the first view from the RecyclerView and click it to open the NoteActivity
        onView(withId(R.id.list_items)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Gain access to the first Note within our data
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        int index = 0;
        NoteInfo note = notes.get(index);

        // Check if the selected note within our Data matches the fields in the opened NoteActivity
        onView(withId(R.id.spinner_courses)).check(
                matches(withSpinnerText(note.getCourse().getTitle()))
        );
        onView(withId(R.id.text_note_title)).check(matches(withText(note.getTitle())));
        onView(withId(R.id.text_note_text)).check(matches(withText(note.getText())));
    }
}