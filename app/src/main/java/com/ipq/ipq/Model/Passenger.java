package com.ipq.ipq.Model;


import java.io.Serializable;

public class Passenger implements Serializable
{

    private  String FullName;
    private String Address;
    private String Phone;
    private String Direcation;
    private String IDPass;



    public Passenger(String fullName, String address, String phone,String Dir,String IDp) {
        FullName = fullName;
        Address = address;
        Phone = phone;
        Direcation=Dir;

        IDPass=IDp;


    }
    public String getIDPass() {
        return IDPass;
    }

    public void setIDPass(String IDPass) {
        this.IDPass = IDPass;
    }
    public String getDirecation() {
        return Direcation;
    }

    public void setDirecation(String direcation) {
        Direcation = direcation;
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
                "שם מלא:" + FullName + "\n" +
                "כתובת:" + Address + "\n" +
                "פלאפון:" + Phone + "\n"+
                 "כיוון נסיעה:" + Direcation;

    }
}
