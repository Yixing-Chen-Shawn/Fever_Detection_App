package com.CIS400.fever_detection_app.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.TextView;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.CIS400.fever_detection_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



@RunWith(AndroidJUnit4.class)
public class HealthNewsActivityTest  {
    //Tests the behavior of HealthNewsActivity

    @Rule
    public ActivityScenarioRule<HealthNewsActivity> activityRule = new ActivityScenarioRule<>(HealthNewsActivity.class);
    @Test
    public void apiTest() {
        //This will test whether the response from the API is valid or not
        URL url = null;
        HttpURLConnection con = null;
        DataOutputStream out = null;
        JSONObject response = null;
        JSONArray news = null;
        boolean malformedUrlE = false, ioE = false, jsonE = false;
        //If the try fails, the test will not pass
        try {
            url = new URL("http://newsapi.org/v2/top-headlines?country=us&catagory=health&apiKey=96addd4bc8af46d1881803c589c42e22");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String contentString = content.toString();
            response = new JSONObject(contentString);
            news = (JSONArray) response.get("articles");
            assertTrue("Response <= 0", response.length() > 0);//The test will not pass if the length of the response is not a positive value
            assertTrue("News<5", news.length() >= 5);//The test will not pass if a specific part of response is not greater than or equal to 5
        } catch (MalformedURLException e) {
            e.printStackTrace();
            malformedUrlE=true;
        }catch (IOException e) {
            e.printStackTrace();
            ioE=true;
        } catch (JSONException e) {
            e.printStackTrace();
            jsonE=true;
        }
        assertFalse("malformed URL exception", malformedUrlE);
        assertFalse("IO exception", ioE);
        assertFalse("Json exception", jsonE);

    }
    @Test
    public void clickBeforeLoadedTest0(){
        //Tests what happens when a user clicks the view before the news are loaded
        onView(withId(R.id.news0)).perform(click());//The test will not pass if this statement crushes the program
        }
    @Test
    public void clickBeforeLoadedTest1(){
        onView(withId(R.id.news1)).perform(click());
    }
    @Test
    public void clickBeforeLoadedTestImg(){
        onView(withId(R.id.news0_img)).perform(click());
    }
    @Test
    public void clickAfterLoaded() throws InterruptedException {
        Thread.sleep(5000);
        onView(withId(R.id.news0_img)).perform(click());
    }
}