package com.ipq.ipq.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by IG on 01/11/2016.
 */

public class Location
{
    @SerializedName("Latitude")
    private double Latitude;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Title")
    private String Title;

    public Location(double latitude, double longitude, String title) {
        Latitude = latitude;
        Longitude = longitude;
        Title = title;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
