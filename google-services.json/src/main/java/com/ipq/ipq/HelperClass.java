package com.ipq.ipq;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelperClass
{
   public static ArrayList<String> helperString=new ArrayList<String>();

    public static Bundle PutMessage(int id,int code,String FullName,String FullAddrs,String phone)
    {
        Bundle bd=new Bundle();

        bd.putInt("id",id);
        bd.putInt("code",code);
        bd.putString("FullName",FullName);
        bd.putString("FullAdd",FullAddrs);
        bd.putString("phone",phone);


       return bd;



    }

}
