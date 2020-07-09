package com.inventrohyder.pluralsight_notekeeper;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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

        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        for (int index = 0; index < notes.size(); index++) {
            // Gain access to the Note at the index'th position within our data
            NoteInfo note = notes.get(index);

            // Check if the selected note within our Data matches the fields in the opened NoteActivity
            onView(withId(R.id.spinner_courses)).check(
                    matches(withSpinnerText(note.getCourse().getTitle()))
            );
            onView(withId(R.id.text_note_title)).check(matches(withText(note.getTitle())));
            onView(withId(R.id.text_note_text)).check(matches(withText(note.getText())));

            // If it is NOT the last note, ensure the next button is enabled and click it to move
            // to the next note.
            if (index < notes.size() - 1) {
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click());
            }
        }

        // At the last note, ensure that the next button is NOT present
        onView(withId(R.id.action_next)).check(doesNotExist());

        // Go back to the MainActivity
        pressBack();
    }
}