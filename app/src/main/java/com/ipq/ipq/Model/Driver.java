package com.ipq.ipq.Model;

import java.io.Serializable;

/**
 * Created by IG on 18/08/2015.
 */
public class Driver implements Serializable{

    private String FullName;
    private String Mobile;
    private String Adress;


    private String IdD;
    private String Time;
    private String Date;
    private String Payment;
    public String Dir;

    public String getIdD() {
        return IdD;
    }

    public void setIdD(String idD) {
        IdD = idD;
    }

    public Driver(String fullName,String IdDriver,String date ,String mobile, String Add, String time,  String payment,String Direcation) {
        FullName = fullName;
        Mobile = mobile;
        this.Adress=Add;
     IdD=IdDriver;
     Date=date;
        Time = time;
        Payment = payment;
        Dir=Direcation;
    }
    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }





    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }





    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getDir() {
        return Dir;
    }

    public void setDir(String dir) {
        Dir = dir;
    }

    @Override
    public String toString() {
        return
                "שם נהג:" + FullName + "\n" +
                "פלאפון:" + Mobile + "\n" +
                "זמן יציאה:" + Time + "\n" +
                "תשלום עבור נסיעה:" + Payment+"\n"+
                 "כיוון נסיעה:" +Dir+"\n"
                        +"כתובת הנהג:"+this.Adress;


    }
}
