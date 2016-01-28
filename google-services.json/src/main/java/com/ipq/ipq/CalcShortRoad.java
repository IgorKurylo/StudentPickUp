package com.ipq.ipq;


import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CalcShortRoad
{
    private static  InputStream stream=null;
    private static JSONObject jsonObject=null;
    private static String result="";
    private static String Lat1,Lng1,Lat2,Lng2;
    private static final String ROUTE="routes";
    private static final String LEGS="legs";
    private  static final String Des="distance";
    private  static final String Dur="duration";
    private  static final String End="end_location";
    private  static final String Start="start_location";
    private static final String overview_polyline="overview_polyline";
    private static JSONArray jsonArray=null;
    public  static  String  encodepoint;
    public static  String URL="";

    public  static List<LatLng> latLngs;
    public static ArrayList<Station> initStation() //init the station for calculate the short way to pick up passenger
    {
        ArrayList<Station> stations=new ArrayList<Station>(1);
        stations.add(new Station(0,"A"));
        stations.add(new Station(-1,"B"));
        stations.add(new Station(-1,"C"));
        stations.add(new Station(-1,"D"));
        stations.add(new Station(-1,"E"));
        return  stations;


    }

    public static void  ShowWay(final String[] Adress,final Handler callBack)
            //function show the way from distention to origin via points on the streets of passengers
    {

        int l=Adress.length;
        int i=1;
        URL="http://maps.googleapis.com/maps/api/directions/json?origin=";
        URL+=URLEncoder.encode(Adress[0]);
        URL+="&destination=";
        URL+=URLEncoder.encode(Adress[l-1]);
        URL+="&waypoints=";
        while(i<l-1){

            try {
                URL+=URLEncoder.encode(Adress[i])+URLEncoder.encode("|","UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;

        }
        URL=URL.substring(0,URL.length()-3);
        URL+="&sensor=false";



        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    DefaultHttpClient connection = new DefaultHttpClient();
                    HttpPost post = new HttpPost(URL);
                    HttpResponse httpResponse = connection.execute(post);
                    HttpEntity httpEntity=httpResponse.getEntity();
                    stream=httpEntity.getContent();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(
                                stream, "iso-8859-1"), 8);

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }


                        stream.close();
                    result = sb.toString();
                     }catch (Exception e)
                    {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }
                try {

                    jsonObject=new JSONObject(result);
                    jsonArray=jsonObject.getJSONArray(ROUTE);
                    JSONObject Route=jsonArray.getJSONObject(0);
                    JSONObject overviewPoly=Route.getJSONObject(overview_polyline);
                    encodepoint=overviewPoly.getString("points");
                    JSONArray jsonArray1=Route.getJSONArray(LEGS);
                    latLngs=PolyUtil.decode(encodepoint);

                    JSONObject JsonInfo=jsonArray1.getJSONObject(0);
                    JSONObject Distance=JsonInfo.getJSONObject(Des);
                    JSONObject Time=JsonInfo.getJSONObject(Dur);


                    Bundle bd=new Bundle();

                    bd.putString("Distance",Distance.getString("text"));
                    bd.putString("Time",Time.getString("text"));

                    bd.putSerializable("LatLngs",(Serializable)latLngs);

                    Message msg=Message.obtain();
                    msg.setData(bd);
                    callBack.sendMessage(msg);
                }catch (JSONException e)
                {
                    Log.e("JsonError",e.toString());
                }



            }
        }).start();


    }






    public void TwoPoints(final String Address,final String Address1,final Handler CallBack1)
    {






    }

    public static void  GetDistance(final String Adress1,final String Adress2, final Handler callBack)
    {

        final String  JsonUrl="http://maps.googleapis.com/maps/api/directions/json?origin="+ URLEncoder.encode(Adress1)+"&destination="+URLEncoder.encode(Adress2)+"+&sensor=false";
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    DefaultHttpClient connection = new DefaultHttpClient();
                    HttpPost post = new HttpPost(JsonUrl);
                    HttpResponse httpResponse = connection.execute(post);
                    HttpEntity httpEntity=httpResponse.getEntity();
                    stream=httpEntity.getContent();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(
                            stream, "iso-8859-1"), 8);

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }


                    stream.close();
                    result = sb.toString();
                }catch (Exception e)
                {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }
                try {

                    jsonObject=new JSONObject(result);
                    jsonArray=jsonObject.getJSONArray(ROUTE);
                    JSONObject Route=jsonArray.getJSONObject(0);
                    JSONObject overviewPoly=Route.getJSONObject(overview_polyline);
                    encodepoint=overviewPoly.getString("points");
                    JSONArray jsonArray1=Route.getJSONArray(LEGS);

                    JSONObject JsonInfo=jsonArray1.getJSONObject(0);
                    JSONObject Distance=JsonInfo.getJSONObject(Des);
                    JSONObject Time=JsonInfo.getJSONObject(Dur);
                    JSONObject End_Loc=JsonInfo.getJSONObject(End);
                    JSONObject Start_Loc=JsonInfo.getJSONObject(Start);

                    Bundle bd=new Bundle();

                    bd.putString("Distance",Distance.getString("text"));
                    bd.putString("Time",Time.getString("text"));
                    bd.putString("lat1",End_Loc.getString("lng"));
                    bd.putString("lng1",End_Loc.getString("lat"));
                    bd.putString("lat2",Start_Loc.getString("lng"));
                    bd.putString("lng2",Start_Loc.getString("lat"));

                    Message msg=Message.obtain();
                    msg.setData(bd);
                    callBack.sendMessage(msg);
                }catch (JSONException e)
                {
                    Log.e("JsonError",e.toString());
                }



            }
        }).start();
    }
}





