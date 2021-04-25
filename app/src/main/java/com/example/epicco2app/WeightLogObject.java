package com.example.epicco2app;

import java.util.Calendar;
import java.util.Date;

public class WeightLogObject {

    Integer weight;
    DateAndTime logTime;
    public WeightLogObject(){
        Date time = Calendar.getInstance().getTime();
        this.logTime = new DateAndTime();
        logTime.setDay(time.getDay());
        logTime.setMonth(time.getMonth());
        logTime.setYear(time.getYear());
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public DateAndTime getLogTime() {
        return logTime;
    }

    public void setLogTime(DateAndTime logTime) {
        this.logTime = logTime;
    }
}
