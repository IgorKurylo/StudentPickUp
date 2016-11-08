package com.ipq.ipq.Utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.ipq.ipq.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Currency;

public  class ActivityHelper
{

    public static final String UTF="UTF-8";
    public static Uri uri;
    public static String ILS="ILS";
    public static String DBUSER="IPQ_USER";
    public static final String Email_User="Email";
    public static final String Pass_User="Pass";
    public static final String Addrs_User="Address";
    public static  String HOMEDIR="";
    public static  String COLLEGEDIR="";
    public static  String ALLDIRS="";
    public static String ACTIONGPS="android.location.PROVIDERS_CHANGED";
    public static String GPSFIXLOC="android.location.GPS_FIX_CHANGE";

    public static final String ID_User="ID";

    public static  ConnectivityManager connectivityManager;

    public static void SendSMS(String telephone,Context context)
    {

        uri = Uri.parse(telephone);
        Intent sms = new Intent(Intent.ACTION_SEND);
        sms.putExtra(Intent.EXTRA_TEXT, telephone);
        sms.setData(Uri.parse("smsto:" + telephone));
        sms.setType("text/plain");
        context.startActivity(sms);
    }
    public static void Call(String telephone,Context context)
    {
        Intent phone=new Intent(Intent.ACTION_CALL);
        phone.setData(Uri.parse("tel:" + (telephone)));
        phone.setPackage("com.android.phone");
        //context.startActivity(phone);
    }
    public static String readStream(InputStream in)
    {
        String response="",line="";
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb=new StringBuilder();
        try{
            while ((line=reader.readLine())!=null)
            {
                sb.append(line);
            }
            response=sb.toString();
        }catch (IOException e)
        {
            Log.e("Error",e.toString());
        }
        return response;


    }

    public static String ToConnectString(String str1,String str2)
    {
        return str1+" "+str2;
    }

    public static void ShowProgressDialog(ProgressDialog progressDialog, String Message)
    {

        progressDialog.setMax(0);
        progressDialog.setMessage(Message);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public static String GetLocaleCurrency()
    {

        Currency cur= Currency.getInstance(ILS);

        return cur.getSymbol();
    }
    public static  boolean CheckConnenction(Context context)
    {

         connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
         if(connectivityManager!=null)
         {
             NetworkInfo info=connectivityManager.getActiveNetworkInfo();
             if(info!=null)
             {

                if(info.isConnected())
                {
                    return true;
                }
                if(info.isAvailable())
                {
                    return true;
                }

             }
             return false;
         }
        return false;


    }


}
