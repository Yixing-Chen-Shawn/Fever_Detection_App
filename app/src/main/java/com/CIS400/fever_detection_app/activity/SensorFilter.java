package com.CIS400.fever_detection_app.activity;

public class SensorFilter {
    private SensorFilter(){}

    public static float sum(float[] arr){
        float retval = 0;
        for(int i = 0; i < arr.length; i++) {
            retval += arr[i];
        }
        return retval;
    }

    public static float[] cross(float[] arr1, float[] arr2){
        float[] ret_arr = new float[3];
        ret_arr[0] = arr1[1] * arr2[2] - arr1[2] * arr2[1];
        ret_arr[1] = arr1[2] * arr2[0] - arr1[0] * arr2[2];
        ret_arr[2] = arr1[0] * arr2[1] - arr1[1] * arr2[0];
        return ret_arr;
    }

    public static float norm(float[] arr){
        float retval = 0;
        for(int i = 0; i < arr.length; i++) {
            retval += arr[i] * arr[i];
        }
        return (float) Math.sqrt(retval);
    }

    public static float dot(float[] a, float[] b) {
        float retval = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
        return retval;
    }

    public static float[] normalize(float[] a) {
        float[] retval = new float[a.length];
        float norm = norm(a);
        for (int i = 0; i < a.length; i++) {
            retval[i] = a[i] / norm;
        }
        return retval;
    }
}
