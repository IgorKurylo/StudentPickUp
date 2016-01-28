package com.ipq.ipq;


import java.io.Serializable;

public class Passenger implements Serializable
{

    private  String FullName;
    private String Address;
    private String Phone;

    public Passenger(String fullName, String address, String phone) {
        FullName = fullName;
        Address = address;
        Phone = phone;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return
                "Name:" + FullName + "\n" +
                "Address:" + Address + "\n" +
                "Phone:" + Phone + "\n";

    }
}
