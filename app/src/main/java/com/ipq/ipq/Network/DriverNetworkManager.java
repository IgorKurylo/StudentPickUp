package com.ipq.ipq.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.Utils.ActivityHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 */

public class DriverNetworkManager extends AsyncTask<Object,String,List<Driver>>
{
    private INetworkResult inetworkResult;
    private String URL;
    public DriverNetworkManager(String Url)
    {
        this.URL=Url;
    }
    @Override
    protected List<Driver> doInBackground(Object... params)
    {
        HttpURLConnection httpURLConnection=null;

        String content="",res="";

        try
        {
            URL url=new URL(URL);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=new BufferedOutputStream(httpURLConnection.getOutputStream());
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());

                res= ActivityHelper.readStream(inputStream);
            }else
            {
                InputStream inputStream=new BufferedInputStream(httpURLConnection.getErrorStream());

                res=ActivityHelper.readStream(inputStream);
            }
            try{

                JSONObject jsonObject=new JSONObject(res);


            }catch (JSONException ex)

            {
                Log.e("ErrorJson",ex.toString());
            }


        }catch (IOException ex)
        {
            Log.e("ErrorIO",ex.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Driver> objects)
    {
        super.onPostExecute(objects);
        if(inetworkResult!=null)
        {
            inetworkResult.Result(objects);
        }
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    public interface  INetworkResult
    {
        void Result(List<Driver> result);

    }
    public void setInetworkResult(INetworkResult inetworkResult)
    {
        this.inetworkResult = inetworkResult;
    }

}
