package com.example.epicco2app;

import java.util.Calendar;
import java.util.Date;

public class WeightLogObject {

    Integer weight;
    Date logTime;
    public WeightLogObject(Integer userWeight){
        this.weight = userWeight;
        this.logTime = Calendar.getInstance().getTime();
        System.out.println(weight);
    }

    public void printWeight() {
        System.out.println(weight);
    }
    public void printTime() {
        System.out.println(logTime.toString());
    }
    public Integer getWeight(){
        return weight;
    }
    public Date getLogTime() {return logTime;}
    public void setTotal(Integer userWeight){
        this.weight = userWeight;
    }
    public void setLogTime(Date dataTime) {this.logTime = dataTime;}
}
