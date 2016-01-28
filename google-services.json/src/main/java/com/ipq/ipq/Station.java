package com.ipq.ipq;


public class Station //  class station passenger and driver
{

    private int Distance;
    private String desStation;

    public Station(int distance, String Name) {

        Distance=distance;
        desStation=Name;
    }



    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }

    public String getNameStation() {
        return desStation;
    }

    public void setNameStation(String nameStation) {
        desStation = nameStation;
    }
}
