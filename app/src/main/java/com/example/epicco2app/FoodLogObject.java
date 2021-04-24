package com.example.epicco2app;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FoodLogObject {

    Long total;
    DateAndTime logTime;
    public FoodLogObject(){
        Date time = Calendar.getInstance().getTime();
        this.logTime = new DateAndTime();
        logTime.setDay(time.getDay());
        logTime.setMonth(time.getMonth());
        logTime.setMonth(time.getYear());
    }

    public void printTot() {
        System.out.println(total);
    }
    public void printTime() {
        System.out.println(logTime.toString());
    }
    public Long getTotal(){
        return total;
    }
    public DateAndTime getLogTime() {return logTime;}
    public void setTotal(Long tot){
        this.total = tot;
    }
    public void setLogTime(DateAndTime dataTime) {this.logTime = dataTime;}
}
