package com.ipq.ipq.Utils;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelperClass
{


    public static Bundle PutMessage(int id, String FullName, String FullAddrs, String phone)
    {
        Bundle bd=new Bundle();
        bd.putInt("id",id);
        bd.putString("FullName",FullName);
        bd.putString("FullAdd",FullAddrs);
        bd.putString("phone",phone);



        return bd;



    }

}
