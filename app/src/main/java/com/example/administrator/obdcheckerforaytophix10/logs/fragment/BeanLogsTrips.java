package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

/**
 * Created by CHD on 2017/9/12.
 */

public class BeanLogsTrips {

    private String distance , fuel , fuel_economy , title;

    public String getDistance() {
        return distance;
    }

    public String getTitle() {
        return title;
    }

    public BeanLogsTrips setTitle(String title) {
        this.title = title;
        return this;
    }

    public BeanLogsTrips setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    public String getFuel() {
        return fuel;
    }

    public BeanLogsTrips setFuel(String fuel) {
        this.fuel = fuel;
        return this;
    }

    public String getFuel_economy() {
        return fuel_economy;
    }

    public BeanLogsTrips setFuel_economy(String fuel_economy) {
        this.fuel_economy = fuel_economy;
        return this;
    }

}
