package com.ipq.ipq.Model;


public class Post
{

    private int DriverId;
    private String Date;
    private String Time;
    private String FullName;
    private String Direction;
    private String Payment;

    public Post(int driverId,String date,String time, String direction, String payment) {
        DriverId = driverId;

        Date = date;
        Time = time;
        Direction = direction;
        Payment = payment;

    }

    public int getDriverId() {
        return DriverId;
    }

    public void setDriverId(int driverId) {
        DriverId = driverId;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    @Override
    public String toString() {
        return  "שם: " + FullName +"\n"+
                "תאריך:" + Date + "\n" +
                "זמן:" + Time + "\n" +
                "כיוון נסיעה:" + Direction + "\n" +
                "תשלום עבור נסיעה:" + Payment;


    }
}
