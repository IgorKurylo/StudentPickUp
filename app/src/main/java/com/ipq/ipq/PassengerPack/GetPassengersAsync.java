package com.ipq.ipq.PassengerPack;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipq.ipq.Adapters.PassengerRecyleAdapter;
import com.ipq.ipq.Model.College;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.Utils.ActivityHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Get Passengers
 */
public class GetPassengersAsync extends AsyncTask<String,String,List<Passenger>>
{
    private String direcation;
    public  String content="", result = "";
    private String Email;
    private String Webservice;
    private Context mContext;
    private PassengerRecyleAdapter passengerAdp;
    private PassengerRecyleAdapter.OnItemClickListener onItemClickListener;
    private RecyclerView listView;
    public ProgressBar progressBar;
    public TextView nodata;
    public GetPassengersAsync(Context context, String direcation, String EmailD, RecyclerView listView , String webservice,
                              PassengerRecyleAdapter.OnItemClickListener onItemClickListener, ProgressBar pBar, TextView textnodata) {
        this.direcation = direcation;
        this.Email = EmailD;
        Webservice = webservice;
        this.listView=listView;
        this.onItemClickListener=onItemClickListener;
        mContext=context;
        this.progressBar=pBar;
        this.nodata=textnodata;

    }

    @Override
    protected List<Passenger> doInBackground(String... params)
    {
        ArrayList<Passenger> listofpassenger = new ArrayList<>();

        try
        {
            content="Email="+ URLEncoder.encode(this.Email,ActivityHelper.UTF)
                    +"&Dir="+URLEncoder.encode(this.direcation,ActivityHelper.UTF);


        } catch (Exception ex) {
            Log.e("ENCODEEXEPTION", ex.toString());
        }
        InputStream reader;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Webservice);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(content);
            wr.flush();
            wr.close();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                reader = new BufferedInputStream(urlConnection.getInputStream());
                result = ActivityHelper.readStream(reader);
            }else{
                reader = new BufferedInputStream(urlConnection.getErrorStream());
                result = ActivityHelper.readStream(reader);

            }
        } catch (IOException ex) {
            Log.e("IOException ", ex.toString());
        }

            try{

                JSONArray jsonArray=new JSONArray(result);
                if(jsonArray.length()>0)
                {
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        listofpassenger.add(new Passenger(jsonObject.getString("fullname")
                                ,jsonObject.getString("address")
                                ,jsonObject.getString("phone"),
                                jsonObject.getString("email")
                                ,new College(i+1+100,jsonObject.getString("colname"))));
                    }
                }
            }catch (JSONException ex)
            {
                Log.e("HomeJson",result);
            }




        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
        }
        return listofpassenger;
    }

    @Override
    protected void onPostExecute(List<Passenger> passengers)
    {
        super.onPostExecute(passengers);
        progressBar.setVisibility(View.GONE);
        if(passengers.size()>0)
        {
            passengerAdp = new PassengerRecyleAdapter(this.mContext, passengers);
            passengerAdp.setItemClickListener(onItemClickListener);
            listView.setAdapter(passengerAdp);
        }else
        {
            nodata.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
