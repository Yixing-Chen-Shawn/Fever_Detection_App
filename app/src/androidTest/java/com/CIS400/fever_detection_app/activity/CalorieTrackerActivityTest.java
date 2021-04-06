package com.CIS400.fever_detection_app.activity;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalorieTrackerActivityTest {

    @Test
    public void getCalorieCalculation() {

        Double total = 0.0;
        Double expected = 1541.0;

        Boolean male = true, female = false;
        Boolean littleActive = true, slightlyActive = false, moderatelyActive = false, veryActive = false;
        String height = "72";
        String age = "21";


        if ( (age == "" || age == null) || (height == "" || height == null) ) {
            assertTrue(false);
        }

        if (male) {

            if (littleActive) {
                total = (66 + (6.2 * (Integer.parseInt(height))) + (12.7 * (Integer.parseInt(height))) - (6.76 * (Integer.parseInt(age)))) * 1.2;

            }
            else if (slightlyActive) {
                total = (66 + (6.2 * (Integer.parseInt(height))) + (12.7 * (Integer.parseInt(height))) - (6.76 * (Integer.parseInt(age)))) * 1.37;
            }
            else if (moderatelyActive) {
                total = (66 + (6.2 * (Integer.parseInt(height))) + (12.7 * (Integer.parseInt(height))) - (6.76 * (Integer.parseInt(age)))) * 1.55;
            }
            else if (veryActive) {
                total = (66 + (6.2 * (Integer.parseInt(height))) + (12.7 * (Integer.parseInt(height))) - (6.76 * (Integer.parseInt(age)))) * 1.725;
            }


        }

        // FOR FEMALES
        else if (female) {

            if (littleActive) {
                total = (655.1 + (4.35 * (Integer.parseInt(height))) + (4.7 * (Integer.parseInt(height))) - (4.7 * (Integer.parseInt(age)))) * 1.2;
            }
            else if (slightlyActive) {
                total = (655.1 + (4.35 * (Integer.parseInt(height))) + (4.7 * (Integer.parseInt(height))) - (4.7 * (Integer.parseInt(age)))) * 1.37;
            }
            else if (moderatelyActive) {
                total = (655.1 + (4.35 * (Integer.parseInt(height))) + (4.7 * (Integer.parseInt(height))) - (4.7 * (Integer.parseInt(age)))) * 1.55;
            }
            else if (veryActive) {
                total = (655.1 + (4.35 * (Integer.parseInt(height))) + (4.7 * (Integer.parseInt(height))) - (4.7 * (Integer.parseInt(age)))) * 1.725;
            }

        }

        else if (male == false || female == false) {
            assertTrue(false);
        }

        assertEquals(expected,total,1);
    }
}