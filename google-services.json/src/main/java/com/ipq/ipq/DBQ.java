package com.ipq.ipq;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import static android.os.StrictMode.ThreadPolicy;

public  class DBQ
{
    public static int code,count;
    public static InputStream is = null;
    private static String result = "",Adress,Mobile,Name,dir,time,date,pay,ID_driver;
    public static JSONObject jsonObject = null;
    public static  ArrayList<JSONObject> jsonObjectArrayList=new ArrayList<JSONObject>();
    public static JSONArray jsonArray = null;
    public static StringBuilder sb;
    public static int valid=0;
    public static Bundle bd;
    public static ArrayList<Passenger> passengers=new ArrayList<Passenger>();
    public static ArrayList<Driver> Drivers=new ArrayList<Driver>();



    public static void SignUp(final String InsertQ,final User user,final Handler hd) {

        new Thread(new Runnable() {




            @Override
            public void run() {

               try {

                   List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                   nameValueParis.add(new BasicNameValuePair("id", user.getId()));
                   nameValueParis.add(new BasicNameValuePair("Email", user.getEmail()));
                   nameValueParis.add(new BasicNameValuePair("Pass", user.getPass()));
                   nameValueParis.add(new BasicNameValuePair("Address", user.getAddress()));
                   nameValueParis.add(new BasicNameValuePair("Phone", user.getTelNumber()));
                   nameValueParis.add(new BasicNameValuePair("FullName",user.getFullName() ));
                   nameValueParis.add(new BasicNameValuePair("City",user.getCity() ));
                   HttpClient httpClient = new DefaultHttpClient();
                   HttpPost httpPost = new HttpPost(InsertQ);
                   httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                   HttpResponse response = httpClient.execute(httpPost);
                   HttpEntity entity = response.getEntity();
                   is = entity.getContent();
               }catch (Exception e)
               {
                   Log.e("Network Tag","Error in Query"+e.toString());
               }

try {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
    }

    is.close();
}catch (Exception e)
{
    Log.e("BufferTag",e.toString());
}
                try {
                    String result = sb.toString();
                    jsonObject = new JSONObject(result);
                    code = (jsonObject.getInt("code"));
                    if(code==1)
                    {
                      hd.sendEmptyMessage(1);
                    }
                }catch (JSONException e)
                {
                    Log.e("JsonTag",e.toString());
                    hd.sendEmptyMessage(-1);
                }



        }


        }).start();



    }

    public static void ShowData(final String SelecQ)  {

        new Thread() {

            @Override
            public void run() {
                super.run();
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                try

                {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(SelecQ);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success ");
                }

                catch(Exception e )
                {
                    Log.e("Web Service 1", e.toString());

                }
                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                }
                catch( IOException e )
                {
                    Log.e("Log:", e.toString());
                }
                try
                {
                    jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");

                }

                catch( JSONException e  )



                {
                    Log.e("JsonTag",e.toString());
                }

            }
        }.start();


    }

    public static void LoginUser(final String Email,final String Pass, final String SelectByEmail,final Handler cB) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Email",Email) );
                nameValuePairs.add(new BasicNameValuePair("Pass",Pass));
                try
                {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(SelectByEmail);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                   if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.e("pass 1", "connection success ");
                   }else{

                       cB.sendEmptyMessage(-1);
                   }
                }

                catch (IOException e)
                {
                    Log.e("IO Exception", e.getMessage());
                }
                catch (Exception e)
                {
                    Log.e("Error", e.getMessage());
                }
                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                }
                catch(IOException e)

                {
                    Log.e("Pass","Connection 2 pass");
                }
                try
                {
                        jsonObject = new JSONObject(result);
                        code = jsonObject.getInt("code");

                        String FullName="";
                        String FullAdd="";
                        FullName+=" "+jsonObject.getString("fullname");
                        FullAdd=jsonObject.getString("address");
                        FullAdd+=" "+jsonObject.getString("city");
                        int id=jsonObject.getInt("ID");
                        String phone=jsonObject.getString("phone");
                        if(code==1)
                        {
                          Message msg = Message.obtain();
                         bd=HelperClass.PutMessage(id,code,FullName,FullAdd,phone);
                         msg.setData(bd);
                         cB.sendMessage(msg);

                         }
                }
                catch(JSONException e)
                {
                    Log.e("Log json:", e.toString());
                    cB.sendEmptyMessage(-1);

                }



            }
        }).start();
        }

    public static void AddDriving(final String Query,final Post driving,final Handler Hd)

    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                    nameValueParis.add(new BasicNameValuePair("Driverid", String.valueOf(driving.getDriverId())));
                    nameValueParis.add(new BasicNameValuePair("dateride", driving.getDate()));
                    nameValueParis.add(new BasicNameValuePair("timeride", driving.getTime()));
                    nameValueParis.add(new BasicNameValuePair("direcation", driving.getDirection()));
                    nameValueParis.add(new BasicNameValuePair("payment", driving.getPayment()));
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Query);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                }catch (Exception e)
                {
                    Log.e("Network Tag","Error in Query"+e.toString());
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    is.close();
                    result=sb.toString();
                }catch (Exception e)
                {
                    Log.e("BufferTag",e.toString());
                }
                try {

                    jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");

                    if(code==1)
                    {
                        Hd.sendEmptyMessage(1);
                    }
                }catch (JSONException e)
                {
                    Log.e("JsonTag",e.toString());
                    Hd.sendEmptyMessage(-1);
                }



            }
        }).start();


    }
    public static void UpdateData(final String Query,final int Id,final String Adrss,final String Phone,final String City,final String Pass,final Handler callBack)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                    nameValueParis.add(new BasicNameValuePair("ID", String.valueOf(Id)));
                    nameValueParis.add(new BasicNameValuePair("Phone", Phone));
                    nameValueParis.add(new BasicNameValuePair("Address",Adrss ));
                    nameValueParis.add(new BasicNameValuePair("City",City) );
                    nameValueParis.add(new BasicNameValuePair("Pass", Pass));

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Query);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                    HttpResponse response = httpClient.execute(httpPost);
                    if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
                    {
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }
                }
                catch (Exception e)
                {
                    Log.e("Network Tag","Error in Query"+e.toString());
                    callBack.sendEmptyMessage(-1);
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result=sb.toString();
                }catch (Exception e)
                {
                    Log.e("BufferTag",e.toString());
                    callBack.sendEmptyMessage(-1);
                }
                try
                {
                    jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");
                    if(code==1)
                    {
                        callBack.sendEmptyMessage(1);
                    }
                }
                catch (JSONException e)
                {
                    Log.e("JsonTag",e.toString());
                   callBack.sendEmptyMessage(-1);
                }
            }
        }).start();



    }
    public static void GetAddress(final String ShowQ,final int ID,final Handler callBack )
    {


        new Thread(new Runnable() {
            @Override
            public void run() {

           try{
                List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                nameValueParis.add(new BasicNameValuePair("ID", String.valueOf(ID)));
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(ShowQ);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                HttpResponse response = httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
                {
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                }
            }
            catch (Exception e)
            {
                Log.e("Network Tag","Error in Query"+e.toString());
                callBack.sendEmptyMessage(-1);
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                result=sb.toString();
            }catch (Exception e)
            {
                Log.e("BufferTag",e.toString());
                callBack.sendEmptyMessage(-1);
            }
            try
            {
                jsonObject = new JSONObject(result);
                code = jsonObject.getInt("code");
                String FullAdress1="";
                String FullAdress2="";

                FullAdress1=jsonObject.getString("0");
                int index1,index2;
                index1=FullAdress1.indexOf(":");
                index2=FullAdress1.indexOf("phone");
                String str=FullAdress1.substring(index1+2,index2-3);
                FullAdress2=jsonObject.getString("1");

                index1=FullAdress2.indexOf(":");
                index2=FullAdress2.indexOf("phone");
                String str1=FullAdress2.substring(index1+2,index2-3);
                if(code==1)
                {
                    Bundle bd=new Bundle();
                    Message msg=Message.obtain();
                    bd.putString("address1",str);
                    bd.putString("address2",str1);
                    msg.setData(bd);

                    callBack.sendMessage(msg);
                }
            }
            catch (JSONException e)
            {
                Log.e("JsonTag",e.toString());
                callBack.sendEmptyMessage(-1);
            }
        }





        }).start();



    }
    public  static void ShowMyPassenger(final String SelectQ,final int ID,final Handler callB)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                    nameValueParis.add(new BasicNameValuePair("ID", String.valueOf(ID)));
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(SelectQ);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                    HttpResponse response = httpClient.execute(httpPost);
                    if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
                    {
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }
                }
                catch (Exception e)
                {
                    Log.e("Network Tag","Error in Query"+e.toString());
                    callB.sendEmptyMessage(-1);
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result=sb.toString();
                }catch (Exception e)
                {
                    Log.e("BufferTag",e.toString());
                    callB.sendEmptyMessage(-1);
                }
                try {
                    jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");
                    count=jsonObject.getInt("count");
                     passengers.clear();
                    for(int i=0;i<count;i++) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(""+(i));
                        Name=jsonObject1.getString("fullname");
                        Adress=jsonObject1.getString("address");
                        Mobile=jsonObject1.getString("phone");
                        passengers.add(new Passenger(Name,Adress,Mobile));

                    }
                    Bundle bd=new Bundle();
                    Message msg=Message.obtain();
                    bd.putSerializable("pass",(Serializable)passengers);
                    msg.setData(bd);
                    callB.sendMessage(msg);
                }catch (JSONException e)
                {
                    Log.e("JsonError",e.toString());
                    callB.sendEmptyMessage(-1);
                }

            }
        }).start();

    }

    public static void ShowDrivers(final String SelectQ,final String Date,final String Dir,final String City,final Handler callBack)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                    nameValueParis.add(new BasicNameValuePair("date", Date));
                    nameValueParis.add(new BasicNameValuePair("dir", Dir));
                    nameValueParis.add(new BasicNameValuePair("City", City));
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(SelectQ);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                    HttpResponse response = httpClient.execute(httpPost);
                    if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
                    {
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }
                }
                catch (Exception e)
                {
                    Log.e("Network Tag","Error in Query"+e.toString());
                    callBack.sendEmptyMessage(-1);
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result=sb.toString();
                }catch (Exception e)
                {
                    Log.e("BufferTag",e.toString());
                    callBack.sendEmptyMessage(-1);
                }
                try {
                    jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");
                    if(code==1) {
                        count = jsonObject.getInt("count");
                        Drivers.clear();
                        for (int i = 0; i < count; i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("" + (i));
                            Name = jsonObject1.getString("fullname");
                            Mobile = jsonObject1.getString("phone");
                            time = jsonObject1.getString("timeride");
                            pay = jsonObject1.getString("pay");
                            ID_driver = jsonObject1.getString("id");
                            Drivers.add(new Driver(Name, ID_driver, Mobile, time, pay));
                        }

                        Bundle bd = new Bundle();
                        Message msg = Message.obtain();
                        bd.putSerializable("driver", (Serializable) Drivers);
                        msg.setData(bd);
                        callBack.sendMessage(msg);
                    }else{
                        callBack.sendEmptyMessage(-1);
                    }
                }catch (JSONException e)
                {
                    Log.e("JsonError",e.toString());
                    callBack.sendEmptyMessage(-1);
                }

            }
        }).start();

    }
    public static void AddPass(final String ID_driver,final String idPass,final Handler callBack,final String AddPass)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
                    nameValueParis.add(new BasicNameValuePair("idD", ID_driver));

                    nameValueParis.add(new BasicNameValuePair("idpass", idPass));


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(AddPass);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValueParis));
                    HttpResponse response = httpClient.execute(httpPost);
                    if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
                    {
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }
                }
                catch (Exception e)
                {
                    Log.e("Network Tag","Error in Query"+e.toString());
                    callBack.sendEmptyMessage(-1);
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result=sb.toString();
                }catch (Exception e)
                {
                    Log.e("BufferTag",e.toString());
                    callBack.sendEmptyMessage(-1);
                }
                try {
                    jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");
                    if(code==1)
                    {
                        callBack.sendEmptyMessage(1);
                    }
                    else{
                        callBack.sendEmptyMessage(-1);
                    }
                }catch (JSONException e)
                {
                    Log.e("Error",e.toString());
                    callBack.sendEmptyMessage(-1);
                }


            }
        }).start();
    }

}
