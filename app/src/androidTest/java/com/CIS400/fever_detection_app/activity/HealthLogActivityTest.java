package com.CIS400.fever_detection_app.activity;

import android.app.Activity;
import android.os.IBinder;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.adapters.HrRecyclerViewAdapter;
import com.google.android.material.card.MaterialCardView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@MediumTest
public class HealthLogActivityTest {

    @Rule
    public ActivityScenarioRule<ManualHealthActivity> rule = new ActivityScenarioRule<ManualHealthActivity>(ManualHealthActivity.class);

    @Test
    //The test is passed with all correct input loaded before click.
    public void clickBeforeLoadedTest0() {
        onView(withId(R.id.dateInput_heart_rate)).perform(typeText("200"));
        onView(withId(R.id.dateInput_contacts)).perform(typeText("4"));
        onView(withId(R.id.dateInput_bodyTemp)).perform(typeText("34"));
        onView(withId(R.id.dateInput_bloody_pressure)).perform(typeText("23"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.saveButton_health)).perform(click());
    }

    @Test
    //The test is will pass if any input field has input number less than 0, and a toast message will be shown.
    public void clickBeforeLoadedTest1() {
        onView(withId(R.id.dateInput_heart_rate)).perform(typeText("-200"));
        onView(withId(R.id.dateInput_contacts)).perform(typeText("4"));
        onView(withId(R.id.dateInput_bodyTemp)).perform(typeText("34"));
        onView(withId(R.id.dateInput_bloody_pressure)).perform(typeText("23"));
        closeSoftKeyboard();
        onView(withId(R.id.saveButton_health)).perform(click());
        onView(withText("Error: Cannot be less than zero")).inRoot(withDecorView(not(getCurrentActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    public Activity getCurrentActivity() {
        //Helper function that gets the current running activity
        final Activity[] currentActivity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Collection<Activity> allActivities = ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(Stage.RESUMED);
                if (!allActivities.isEmpty()) {
                    currentActivity[0] = allActivities.iterator().next();
                }
            }
        });
        return currentActivity[0];
    }

}
