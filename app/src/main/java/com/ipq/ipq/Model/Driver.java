package com.ipq.ipq.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Driver extends User implements Serializable
{


    @SerializedName("TimeOut")
    private String Timeout;
    @SerializedName("TimeBack")
    private String Timeback;
    @SerializedName("DateRide")
    private String Date_Time;
    @SerializedName("To")
    private Address To;
    @SerializedName("From")
    private Address From;
    @SerializedName("StudentInRide")
    private int Sit_Number;
    @SerializedName("Price")
    private float  Price;
    private String Note;
    private String Car_Type;


    public Driver(String uniqueId, String password) {
        super(uniqueId, password);
    }

    public Driver(String uniqueId, String password, String car_Type, String date_Time, Address from, String note, float price, int sit_Number, String timeback, String timeout, Address to)
    {
        super(uniqueId, password);
        Car_Type = car_Type;
        Date_Time = date_Time;
        From = from;
        Note = note;
        Price = price;
        Sit_Number = sit_Number;
        Timeback = timeback;
        Timeout = timeout;
        To = to;
    }

    public String getCar_Type() {
        return Car_Type;
    }

    public void setCar_Type(String car_Type) {
        Car_Type = car_Type;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(String date_Time) {
        Date_Time = date_Time;
    }

    public Address getFrom() {
        return From;
    }

    public void setFrom(Address from) {
        From = from;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getSit_Number() {
        return Sit_Number;
    }

    public void setSit_Number(int sit_Number) {
        Sit_Number = sit_Number;
    }

    public String getTimeback() {
        return Timeback;
    }

    public void setTimeback(String timeback) {
        Timeback = timeback;
    }

    public String getTimeout() {
        return Timeout;
    }

    public void setTimeout(String timeout) {
        Timeout = timeout;
    }

    public Address getTo() {
        return To;
    }

    public void setTo(Address to) {
        To = to;
    }
}
