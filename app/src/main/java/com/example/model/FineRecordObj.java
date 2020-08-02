package com.example.model;

// POJO class to store the fined tickets fetched from/stored in firebase database
public class FineRecordObj {
    private String car_NUMBER;
    private String due_DATE;
    private int fine_AMT;
    private String fined_BY;
    private String fine_DATE;
    private String fine_TICKETID;
    private boolean license_STATUS;
    private String status;
    private String fined_BYID;
    private String ruledesc_LIST;

    public String getCar_NUMBER() {
        return car_NUMBER;
    }

    public void setCar_NUMBER(String car_NUMBER) {
        this.car_NUMBER = car_NUMBER;
    }

    public String getDue_DATE() {
        return due_DATE;
    }

    public void setDue_DATE(String due_DATE) {
        this.due_DATE = due_DATE;
    }

    public int getFine_AMT() {
        return fine_AMT;
    }

    public void setFine_AMT(int fine_AMT) {
        this.fine_AMT = fine_AMT;
    }

    public String getFined_BY() {
        return fined_BY;
    }

    public void setFined_BY(String fined_BY) {
        this.fined_BY = fined_BY;
    }

    public String getFined_BYID() {
        return fined_BYID;
    }

    public void setFined_BYID(String fined_BYID) {
        this.fined_BYID = fined_BYID;
    }


    public String getFine_DATE() {
        return fine_DATE;
    }

    public void setFine_DATE(String fine_DATE) {
        this.fine_DATE = fine_DATE;
    }

    public String getFine_TICKETID() {
        return fine_TICKETID;
    }

    public void setFine_TICKETID(String fine_TICKETID) {
        this.fine_TICKETID = fine_TICKETID;
    }

    public boolean isLicense_STATUS() {
        return license_STATUS;
    }

    public void setLicense_STATUS(boolean license_STATUS) {
        this.license_STATUS = license_STATUS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuledesc_LIST() {
        return ruledesc_LIST;
    }

    public void setRuledesc_LIST(String ruledesc_LIST) {
        this.ruledesc_LIST = ruledesc_LIST;
    }
}
