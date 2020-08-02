package com.example.model;

// POJO class to store the carmetadata fetched from firebase database
public class CarMetaData {

    private String car_MODEL;
    private String car_NUMBER;
    private String owner_MAIL;
    private String owner_NAME;
    private String owner_PHONE;
    private int seqid;
    private String ssn;
    private String state;
    private int zipcode;

    public String getCar_MODEL() {
        return car_MODEL;
    }

    public void setCar_MODEL(String car_MODEL) {
        this.car_MODEL = car_MODEL;
    }

    public String getCar_NUMBER() {
        return car_NUMBER;
    }

    public void setCar_NUMBER(String car_NUMBER) {
        this.car_NUMBER = car_NUMBER;
    }

    public String getOwner_MAIL() {
        return owner_MAIL;
    }

    public void setOwner_MAIL(String owner_MAIL) {
        this.owner_MAIL = owner_MAIL;
    }

    public String getOwner_NAME() {
        return owner_NAME;
    }

    public void setOwner_NAME(String owner_NAME) {
        this.owner_NAME = owner_NAME;
    }

    public String getOwner_PHONE() {
        return owner_PHONE;
    }

    public void setOwner_PHONE(String owner_PHONE) {
        this.owner_PHONE = owner_PHONE;
    }

    public int getSeqid() {
        return seqid;
    }

    public void setSeqid(int seqid) {
        this.seqid = seqid;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
