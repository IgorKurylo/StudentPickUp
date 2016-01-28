package com.ipq.ipq.Model;

import com.google.android.gms.maps.model.LatLng;


public class UserPlace
{
    private  String Address;
    private LatLng latLng;

    public UserPlace(String address, LatLng latLng) {
        Address = address;
        this.latLng = latLng;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
