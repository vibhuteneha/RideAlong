package com.ridealong.models;

import java.util.Date;

/**
 * Created by Sridhar16 on 6/4/2016.
 */
public class DriverDetails {

    private int id;
    private int userId;
    private String car_no;
    private String carModel;
    private String from_place;
    private String destination;
    private String leavingDate;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcar_no() {
        return car_no;
    }

    public void setcar_no(String car_no) {
        this.car_no = car_no;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getfrom_place() {
        return from_place;
    }

    public void setfrom_place(String from_place) {
        this.from_place = from_place;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(String leavingDate) {
        this.leavingDate = leavingDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
