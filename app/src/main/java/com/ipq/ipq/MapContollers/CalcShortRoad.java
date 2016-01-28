package com.ipq.ipq.MapContollers;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.ipq.ipq.Model.Station;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
    public  static  ArrayList<Station> stations=null;
    public  static  ArrayList<Station> stations_2=null;

    public static int index=0,index2=0;

    public static String[] NewAdress;
    public static String[] Stations;
    private static final String ROUTE="routes";
    private static final String LEGS="legs";
    private  static final String Des="distance";
    //private  static final String Dur="duration";
    private static final String overview_polyline="overview_polyline";
    private static JSONArray jsonArray=null;
    public  static  String  encodepoint;
    public static  String URL="";
    public static String URL_2="";
    public static String URLTEMP="";
    public static String UrlSort="";
    public static String UrlTmp2="";
    public  static String [] letters={"B","C","D","E"};
    public  static List<LatLng> latLngs_viapoints=new ArrayList<LatLng>();
    public static String[] address=null;
    public  static List<LatLng> latLngs;
    public static ArrayList<Station> initStation(int Psize) //init the station for calculate the short way to pick up passenger
    {
        ArrayList<Station> stations=new ArrayList<Station>();
        stations.add(new Station(0,"A"));
        for(int i=0;i<Psize;i++) {
            stations.add(new Station(-1, letters[i]));

        }
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
        URL=URL.substring(0,URL.length()-3); //orginaze the url
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
                    JSONObject overviewPoly=Route.getJSONObject(overview_polyline); //get polyigone points
                    encodepoint=overviewPoly.getString("points"); //get string of all points
                    JSONArray jsonArray1=Route.getJSONArray(LEGS);
                    latLngs=PolyUtil.decode(encodepoint); //PolyUtil do decode from String overview polyline to point lang and lat
                    JSONObject JsonInfo=jsonArray1.getJSONObject(0);
                    JSONObject Distance=JsonInfo.getJSONObject(Des);
//                    JSONObject Time=JsonInfo.getJSONObject(Dur);
                    address=new String[jsonArray1.length()+1];
                    latLngs_viapoints.clear();
                    /**
                     * loop which take from json array of all points of lat and lang of the passengers and address
                     */
                    for(int i=0;i<jsonArray1.length();i++)
                    {
                        JSONObject start_location= (JSONObject) jsonArray1.getJSONObject(i).get("start_location");
                        address[i]=jsonArray1.getJSONObject(i).getString("start_address");
                        latLngs_viapoints.add(new LatLng(start_location.getDouble("lat"),start_location.getDouble("lng")));

                    }
                    JSONObject end_location=(JSONObject)jsonArray1.getJSONObject(jsonArray1.length()-1).get("end_location");
                    address[jsonArray1.length()]=jsonArray1.getJSONObject(jsonArray1.length()-1).getString("end_address");
                    latLngs_viapoints.add(new LatLng(end_location.getDouble("lat"),end_location.getDouble("lng")));

                    Bundle bd=new Bundle();
                    bd.putString("Distance",Distance.getString("text"));
                    bd.putSerializable("latlngs_viapoints", (Serializable) latLngs_viapoints);
                    bd.putSerializable("LatLngs",(Serializable)latLngs);
                    bd.putStringArray("address",address);
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
    public static void  GetDistance(final String DAddress,final String[] PAddress ,final Handler callBack)
    {
        /**
         * init the stations set the first station to the driver
         */
        //enter the adress of the final dest collage
            index = 0;
            stations = initStation(PAddress.length - 1);
            stations.get(0).setDistance(0);
            stations.get(0).setNameStation(DAddress);
            index++;
        /**
         * init the url for the json request
         */
            URL_2 = "http://maps.googleapis.com/maps/api/directions/json?origin=";
            URL_2 += URLEncoder.encode(DAddress);
            URLTEMP = "&destination=" + URLEncoder.encode(PAddress[0]) + "&sensor=false";
            URL_2 += URLTEMP;

        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * loop which run on all the address of passengers
                 * and driver and check between them distance
                 */
                for(int i=1;i<PAddress.length;i++)
                {

                    try {

                        URL_2 = URL_2.replace(URLTEMP, "&destination=" + URLEncoder.encode(PAddress[i]) + "&sensor=false");
                        DefaultHttpClient connection = new DefaultHttpClient();
                        HttpPost post = new HttpPost(URL_2);
                        HttpResponse httpResponse = connection.execute(post);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        stream = httpEntity.getContent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(
                                stream, "iso-8859-1"), 8);

                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }


                        stream.close();
                        result = sb.toString();
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }
                    try {

                        jsonObject = new JSONObject(result);
                        jsonArray = jsonObject.getJSONArray(ROUTE);
                        JSONObject Route = jsonArray.getJSONObject(0);
                        JSONArray jsonArray1 = Route.getJSONArray(LEGS);
                        JSONObject JsonInfo = jsonArray1.getJSONObject(0);
                        JSONObject Distance = JsonInfo.getJSONObject(Des);
                        stations.get(index).setDistance((Double.parseDouble(Distance.getString("value"))));
                        stations.get(index).setNameStation(PAddress[i]);
                        index++;
                        URLTEMP = "&destination=" + URLEncoder.encode(PAddress[i]) + "&sensor=false";

                        if (index == stations.size()) {

                            Bundle bd = new Bundle();
                            bd.putSerializable("Stations", (Serializable) stations);
                            Message msg = Message.obtain();
                            msg.setData(bd);
                            callBack.sendMessage(msg);
                        }


                    } catch (JSONException e) {
                        Log.e("JsonError", e.toString());
                        callBack.sendEmptyMessage(-1);

                    }catch (Exception e)
                    {
                        Log.e("Error",e.toString());
                        callBack.sendEmptyMessage(-1);
                    }



                }

            }
        }).start();
    }









}








