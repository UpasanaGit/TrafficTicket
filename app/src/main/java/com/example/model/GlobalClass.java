package com.example.model;

import android.app.Application;

// POJO class to store the logged in user details fetched from firebase database to set Globally
public class GlobalClass extends Application {

    private String state;
    private String userid;
    private String username;
    private String user_DOB;
    private String user_MAIL;
    private String user_PHONE;
    private String user_PWD;
    private int zipcode;
    private String childId="";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_DOB() {
        return user_DOB;
    }

    public void setUser_DOB(String user_DOB) {
        this.user_DOB = user_DOB;
    }

    public String getUser_MAIL() {
        return user_MAIL;
    }

    public void setUser_MAIL(String user_MAIL) {
        this.user_MAIL = user_MAIL;
    }

    public String getUser_PHONE() {
        return user_PHONE;
    }

    public void setUser_PHONE(String user_PHONE) {
        this.user_PHONE = user_PHONE;
    }

    public String getUser_PWD() {
        return user_PWD;
    }

    public void setUser_PWD(String user_PWD) {
        this.user_PWD = user_PWD;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }
}