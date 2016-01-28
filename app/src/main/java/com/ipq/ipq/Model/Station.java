package com.ipq.ipq.Model;


public class Station //  class station passenger and driver
{

    private double Distance;
    private String desStation;

    public Station(double distance, String Name) {

        Distance=distance;
        desStation=Name;
    }




    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public String getNameStation() {
        return desStation;
    }

    public void setNameStation(String nameStation) {
        desStation = nameStation;
    }
}
