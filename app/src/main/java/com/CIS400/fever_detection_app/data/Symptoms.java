package com.CIS400.fever_detection_app.data;

import java.lang.reflect.Array;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class Symptoms extends BmobObject {

    //SYMPTOMS VARIABLES
    private String symptomDescription;
    private String symptomDate;



    private String time;
    private String date;
    private MyUser user;

    public String getDescription() {
        return symptomDescription;
    }

    public void setDescription(String description) {
        this.symptomDescription = description;

    }

    public String getSymptomDate() {
        return symptomDescription;
    }

    public void setSymptomDate(String date) {
        this.symptomDate = date;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

}
