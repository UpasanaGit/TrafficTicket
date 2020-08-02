package com.example.model;

// POJO class to store the traffic rules fetched from firebase database
public class TrafficRule {

    private int fineamt;
    private int ruleid;
    private String rule_DESC;
    private String state;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getFineamt() {
        return fineamt;
    }

    public void setFineamt(int fineamt) {
        this.fineamt = fineamt;
    }

    public int getRuleid() {
        return ruleid;
    }

    public void setRuleid(int ruleid) {
        this.ruleid = ruleid;
    }

    public String getRule_DESC() {
        return rule_DESC;
    }

    public void setRule_DESC(String rule_DESC) {
        this.rule_DESC = rule_DESC;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
