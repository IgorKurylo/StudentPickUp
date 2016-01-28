package com.ipq.ipq;

/**
 * Created by IG on 18/08/2015.
 */
public class Driver {

    private String FullName;
    private String Mobile;
    private String IdD;
    private String Time;

    private String Payment;

    public String getIdD() {
        return IdD;
    }

    public void setIdD(String idD) {
        IdD = idD;
    }

    public Driver(String fullName,String IdDriver, String mobile,  String time,  String payment) {
        FullName = fullName;
        Mobile = mobile;
     IdD=IdDriver;

        Time = time;
        Payment = payment;
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

    @Override
    public String toString() {
        return
                "Name:" + FullName + "\n" +
                "Mobile:" + Mobile + "\n" +

                "Time:" + Time + "\n" +

                "Payment:" + Payment;

    }
}
