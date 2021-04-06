package com.CIS400.fever_detection_app.activity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@MediumTest
@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {
    /**This @Rule provides functional testing of a single activity.
    The activity under test is launched before each test annotated with
    @Test and before any method annotated with @Before.**/
    @Rule
    public ActivityScenarioRule<MapsActivity> rule = new ActivityScenarioRule<MapsActivity>(MapsActivity.class);
    @Test
    public void ensureLocationIsPresent() throws Exception{
        /*rule.getScenario().onActivity(activity -> {
            assertNotNull();
        });*/
    }
}
