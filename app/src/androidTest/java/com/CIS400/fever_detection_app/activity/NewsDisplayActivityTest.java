package com.CIS400.fever_detection_app.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.CIS400.fever_detection_app.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.openLink;
import static androidx.test.espresso.action.ViewActions.openLinkWithText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class NewsDisplayActivityTest {
    //Tests the behavior of NewsDisplayActivityTest
    private Bundle b = new Bundle();

    @Rule
    public ActivityScenarioRule<NewsDisplayActivity> activityRule = new ActivityScenarioRule<NewsDisplayActivity>(NewsDisplayActivity.class,addArg());
    private Bundle addArg(){
        b.putString("url", "foo");
        return b;
    }
    @Test
    public void badUrlTest(){
        //the test will pass if the program does not crush
    }
}

