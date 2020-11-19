package com.CIS400.fever_detection_app.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
    private int age;
    private String gender;
    private String phoneNum;

    private String date;
    private String description;
    private String rating;

    private String notificationHadViewed;

    private List<String> descriptions = new ArrayList<>();
    private List<String> dates = new ArrayList<>();
    private List<String> ratings = new ArrayList<>();
    private List<String> heartRate = new ArrayList<>();
    private List<String> contacts = new ArrayList<>();
    private List<String> hrdates = new ArrayList<>();
    private List<String> bodyTemp = new ArrayList<>();
    private List<String> blood = new ArrayList<>();


    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    //Getter and Setters for symptom descriptions
    public void addSymptom (String description) {
        this.descriptions.add(description);
        Collections.reverse(this.descriptions);
    }
    public List<String> getSymptoms() {
        return descriptions;
    }


    public List<String> getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(String heartrate){
        this.heartRate.add(heartrate);
        Collections.reverse(this.heartRate);
    }

    public List<String> getContacts(){
        return contacts;
    }

    public void setContacts(String contacts){
        this.contacts.add(contacts);
        Collections.reverse(this.contacts);
    }

    public List<String> getBodyTemp(){
        return bodyTemp;
    }

    public void setBodyTemp(String bodyTemp){
        this.bodyTemp.add(bodyTemp);
        Collections.reverse(this.bodyTemp);
    }

    public List<String> getBlood(){
        return this.blood;
    }

    public void setBlood(String blood){
        this.blood.add(blood);
        Collections.reverse(this.blood);
    }

    //Miscellaneous symptom functions
    public void clearSymptoms() {
        this.descriptions.clear();
        this.dates.clear();
        this.ratings.clear();
    }
    public void deleteSymptom() {
        this.descriptions.remove(0);
        this.dates.remove(0);
        this.ratings.remove(0);
    }

    public int deleteSymptom(String date) {
        int idx = this.dates.indexOf(date);
        if(idx == -1) return idx;
        else{
            this.dates.remove(idx);
            this.descriptions.remove(idx);
            this.ratings.remove(idx);
        }
        return idx;
    }

    public void clearHealthStats(){
        this.hrdates.clear();
        this.heartRate.clear();
        this.contacts.clear();
        this.bodyTemp.clear();
        this.blood.clear();
    }

    public int deleteHealthStats(String date){
        int idx = this.hrdates.indexOf(date);
        if(idx == -1) return idx;
        else{
            this.hrdates.remove(idx);
            this.heartRate.remove(idx);
            this.contacts.remove(idx);
            this.bodyTemp.remove(idx);
            this.blood.remove(idx);
        }
        return idx;
    }

    public void deleteHealthStats() {
        this.hrdates.remove(0);
        this.heartRate.remove(0);
        this.contacts.remove(0);
        this.bodyTemp.remove(0);
        this.blood.remove(0);
    }


    public String getNotificationViewed() {
        return notificationHadViewed;
    }
    public void setNotificationViewed(String notificationViewed) {
        this.notificationHadViewed = notificationViewed;
    }


    //Getter and Setters for symptom dates
    public List<String> getSymptomDates() {
        return dates;
    }
    public void addSymptomDate(String date) {
        this.dates.add(date);
        Collections.reverse(this.dates);
    }

    //Getter and Setters for symptom ratings
    public List<String> getSymptomRatings() {
        return ratings;
    }
    public void addSymptomRating(String rating) {
        this.ratings.add(rating);
        Collections.reverse(this.ratings);
    }


    public List<String> getHrdates(){
        return hrdates;
    }

    public void setHrdates(String date){
        this.hrdates.add(date);
        Collections.reverse(this.hrdates);
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }
}




