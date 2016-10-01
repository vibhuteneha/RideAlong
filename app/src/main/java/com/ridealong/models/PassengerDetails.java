package com.ridealong.models;

import java.util.Date;

/**
 * Created by Sridhar16 on 6/4/2016.
 */
public class PassengerDetails {

    private int id;
    private String from;
    private String destination;
    private String leavingDate;
    private int userId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
