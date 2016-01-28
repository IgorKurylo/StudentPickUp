package com.ipq.ipq;


public class User {

    private String  Id;
    private String FullName;

    private String Address;
    private String TelNumber;
    private String Email;
    private String Pass;
    private String City;

//    private int ImageID;
    public User(String id, String fullname, String address, String telNumber, String email,String pss,String city) {
        Id = id;
        FullName = fullname;
        Address = address;
        TelNumber = telNumber;
        Email = email;

        City=city;
        Pass=pss;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName =fullName ;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelNumber() {
        return TelNumber;
    }

    public void setTelNumber(String telNumber) {
        TelNumber = telNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    @Override
    public String toString() {
        return "" +
                "Id='" + Id + '\'' +
                ", FullName='" + FullName + '\'' +
                ", Address='" + Address + '\'' +
                ", TelNumber='" + TelNumber + '\'' +
                ", Email='" + Email + '\'';
    }
}
