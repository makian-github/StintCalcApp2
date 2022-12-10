package com.example.stintcalcapp2.model;

import android.app.Application;

import java.io.Serializable;

public class RaceData extends Application implements Serializable {

    private int raceTime = 0;
    private int stint = 0;
    private String startTime = "00:00";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(int raceTime) {
        this.raceTime = raceTime;
    }

    public int getStint() {
        return stint;
    }

    public void setStint(int stint) {
        this.stint = stint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}


