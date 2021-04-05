package com.CIS400.fever_detection_app.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class globalListFragmentTest {
    @org.junit.jupiter.api.Test
    public void apiTesting(){
        URL url = null;
        HttpURLConnection con = null;
        JSONObject response = null;
        boolean malformedUrlE = false, ioE = false, jsonE = false;
        //If the try fails, the test will not pass
        try {
            url = new URL("https://disease.sh/v3/covid-19/countries");
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
            assertTrue("Response <= 0", response.length() > 0);//The test will not pass if the length of the response is not a positive value
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
    @org.junit.jupiter.api.Test
    public void USAData(){
        URL url = null;
        HttpURLConnection con = null;
        JSONObject response = null;
        boolean malformedUrlE = false, ioE = false, jsonE = false;
        //If the try fails, the test will not pass
        try {
            url = new URL("https://disease.sh/v3/covid-19/countries/USA");
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
            assertTrue("Response <= 0", response.length() > 0);//The test will not pass if the length of the response is not a positive value
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
}