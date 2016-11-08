package com.ipq.ipq.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by IG on 01/11/2016.
 */

public class Address
{
    private String City;
    private String Street;
    private String HouseNumber;
    private Location location;

    public Address(String city, String houseNumber, Location location, String street) {
        City = city;
        HouseNumber = houseNumber;
        this.location = location;
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}
