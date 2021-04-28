package com.example.epicco2app;

import java.util.Calendar;
import java.util.Date;

public class WeightLogObject {

    Float weight;
    Float height;
    Float bmi;

    DateAndTime logTime;
    public WeightLogObject(){
        Date time = Calendar.getInstance().getTime();
        this.logTime = new DateAndTime();
        logTime.setDay(time.getDate());
        logTime.setMonth(time.getMonth());
        logTime.setYear(time.getYear()+1900);
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public DateAndTime getLogTime() {
        return logTime;
    }

    public void setLogTime(DateAndTime logTime) {
        this.logTime = logTime;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

}
