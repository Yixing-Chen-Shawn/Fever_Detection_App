package com.CIS400.fever_detection_app.activity;

import android.app.Activity;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.CIS400.fever_detection_app.R;

import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest {

    private LoginActivity loginActivity;

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


    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void clickBeforeLoadedTest0(){
        //Tests what happens when a user clicks the view before the news are loaded

        onView(withId(R.id.input_Email)).perform(typeText("kmatton@syr.edu"));
        onView(withId(R.id.input_password)).perform(typeText("tester"));
        onView(withId(R.id.input_password)).perform(closeSoftKeyboard());

        onView(withId(R.id.button1)).perform(click());//The test will not pass if this statement crushes the program
    }


}