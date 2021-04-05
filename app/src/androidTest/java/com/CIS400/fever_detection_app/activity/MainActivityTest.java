package com.CIS400.fever_detection_app.activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.CIS400.fever_detection_app.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void globalListFragmentTest(){
        onView(withId(R.id.button_Covid)).perform(click());
    }

}