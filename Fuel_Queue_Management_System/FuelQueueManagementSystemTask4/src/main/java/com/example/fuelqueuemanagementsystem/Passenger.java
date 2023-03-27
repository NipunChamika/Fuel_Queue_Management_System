package com.example.fuelqueuemanagementsystem;

public class Passenger {
    private String fName = "";
    private String lName = "";
    private String vNum = "";
    private int fuelLiters = 0;

    //constructor
    Passenger() {
        //constructor
        this.fName="Empty";
        this.lName="Empty";
        this.vNum="Empty";
        this.fuelLiters=0;
    }

    Passenger(String fName, String lName, String vNum, int fuelLiters) {
        this.fName=fName;
        this.lName=lName;
        this.vNum=vNum;
        this.fuelLiters=fuelLiters;
    }
    //encapsulation
    public void setFName(String fName) {
        this.fName = fName;
    }
    public String getFName() {
        return this.fName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }
    public String getLName() {
        return this.lName;
    }

    public void setVNum(String vNum) {
        this.vNum = vNum;
    }

    public String getVNum() {
        return this.vNum;
    }

    public void setFuelLiters(int fuelLiters) {
        this.fuelLiters = fuelLiters;
    }

    public int getFuelLiters() {
        return this.fuelLiters;
    }

    public boolean isEmpty() {
        if (this.fName == "Empty") {
            return true;
        } else {
            return false;
        }
    }
}

