package com.example.epicco2app;

import org.json.JSONException;
import org.json.JSONObject;

public class CO2Data {
    Integer dairy;
    Integer meat;
    Integer plant;
    Integer restaurant;
    Integer total;
    public CO2Data(){

    }



    public Integer getDairy() {
        return dairy;
    }

    public void setDairy(Integer dairy) {
        this.dairy = dairy;
    }

    public Integer getMeat() {
        return meat;
    }

    public void setMeat(Integer meat) {
        this.meat = meat;
    }

    public Integer getPlant() {
        return plant;
    }

    public void setPlant(Integer plant) {
        this.plant = plant;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
