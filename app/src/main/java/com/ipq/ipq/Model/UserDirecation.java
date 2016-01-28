package com.ipq.ipq.Model;

import com.google.android.gms.maps.model.LatLng;


public class UserDirecation {

    private String CollageName;
    private boolean isCollage;
    private String CollageAddress;
    private String WorkAddress;
    private int CollegeID;
    private LatLng latLng;



    public UserDirecation(int ColID,String collageName,String CollageAdd,boolean iscollage,LatLng points) {
        CollageName = collageName;
        this.isCollage=iscollage;
        this.CollegeID=ColID;
        this.latLng=points;
        this.CollageAddress=CollageAdd;

    }
    public UserDirecation(String CollageName,String ColAddress)
    {

    }

    public String getCollageAddress() {
        return CollageAddress;
    }

    public void setCollageAddress(String collageAddress) {
        CollageAddress = collageAddress;
    }

    public UserDirecation( String workAddress,boolean isCollage,LatLng points) {

        WorkAddress = workAddress;
        this.isCollage=isCollage;
        this.latLng=points;


    }
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getCollegeID() {
        return CollegeID;
    }

    public void setCollegeID(int collegeID) {
        CollegeID = collegeID;
    }
    public boolean isCollage() {
        return isCollage;
    }

    public void setCollage(boolean isCollage) {
        this.isCollage = isCollage;
    }
    public String getCollageName() {
        return CollageName;
    }

    public void setCollageName(String collageName) {
        CollageName = collageName;
    }





    public String getWorkAddress() {
        return WorkAddress;
    }

    public void setWorkAddress(String workAddress) {
        WorkAddress = workAddress;
    }
}
