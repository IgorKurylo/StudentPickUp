package com.ipq.ipq.Model;


import com.google.gson.annotations.SerializedName;

public class College
{
    @SerializedName("Name")
    private String NameCollege;
    @SerializedName("Id")
    private int ID;
    public College(int ID, String nameCollege)
    {
        this.ID = ID;
        NameCollege = nameCollege;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameCollege() {
        return NameCollege;
    }

    public void setNameCollege(String nameCollege) {
        NameCollege = nameCollege;
    }
}
