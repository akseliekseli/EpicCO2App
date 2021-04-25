package com.example.epicco2app;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FoodLogObject {

    Integer total;
    DateAndTime logTime;
    public FoodLogObject(){
        Date time = Calendar.getInstance().getTime();
        this.logTime = new DateAndTime();
        logTime.setDay(time.getDate());
        logTime.setMonth(time.getMonth());
        logTime.setYear(time.getYear());
    }

    public void printTot() {
        System.out.println(total);
    }
    public void printTime() {
        System.out.println(logTime.toString());
    }
    public Integer getTotal(){
        return total;
    }
    public DateAndTime getLogTime() {return logTime;}
    public void setTotal(Integer tot){
        this.total = tot;
    }
    public void setLogTime(DateAndTime dataTime) {this.logTime = dataTime;}
}
