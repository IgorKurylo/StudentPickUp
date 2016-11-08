package com.ipq.ipq;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.ipq.ipq.DataBase.DataBaseSqlite;
import com.ipq.ipq.Utils.ActivityHelper;
import com.ipq.ipq.Utils.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class UpdateData extends Fragment
{
    public View update;
    public String phone="",address="",city="",collageName="";
    public String UpdateDetails="";
    public AutoCompleteTextView btnAddress,btnCity;
    public Button btnSubmit;
    public EditText CollageName,btnPhone;
    public int ID;
    public  ArrayList<String> strings=new ArrayList<>();
    public String[] colarr=null;
    public LatLng latLng;
    public String  UrlAutoComplete,Email;
    public ArrayAdapter<String> collegs=null;
    public AlertDialog.Builder AlertColleges=null;
    public ProgressDialog progressDialog;
    public UpdateData()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        update=inflater.inflate(R.layout.up_date_data,container,false);
        btnPhone = (EditText) update.findViewById(R.id.btnPhone);
        final DataBaseSqlite dataBaseSqlite=new DataBaseSqlite(update.getContext());
        Session session=new Session(update.getContext());
        Email=session.GetDetUser(ActivityHelper.Email_User);

        btnAddress=(AutoCompleteTextView) update.findViewById(R.id.btnAddress);
        btnSubmit= (Button)update.findViewById(R.id.submit);
        btnCity= (AutoCompleteTextView)update.findViewById(R.id.btnCity);
        CollageName=(EditText)update.findViewById(R.id.NameofCollage);
        UrlAutoComplete=getResources().getString(R.string.AutoCompleteCollege);
        UpdateDetails=getResources().getString(R.string.UpdateDetails);
        new GetCollegesAsync().execute(UrlAutoComplete);
        CollageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                AlertColleges=new AlertDialog.Builder(update.getContext());

                AlertColleges.setSingleChoiceItems(colarr, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                CollageName.setText(colarr[which]);

                            }
                        });
                        AlertDialog dialog = AlertColleges.create();
                dialog.show();

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone=btnPhone.getText().toString().trim();
                city=btnCity.getText().toString().trim();
                address=btnAddress.getText().toString().trim();
                collageName=CollageName.getText().toString().trim();
              //  dataBaseSqlite.UpdateUserDetailes(Email,city,address,collageName,phone);
                new UpdateDataAsync().execute(phone,address,city,collageName,Email);



            }
        });
        return update;
    }


    class GetCollegesAsync extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            String res="";
            HttpURLConnection httpURLConnection=null;
            try{
                URL url=new URL(params[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setChunkedStreamingMode(0);
                httpURLConnection.setRequestMethod("GET");
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    InputStream in=new BufferedInputStream(httpURLConnection.getInputStream());
                    res=ActivityHelper.readStream(in);
                }else{

                    InputStream in=new BufferedInputStream(httpURLConnection.getErrorStream());
                    res=ActivityHelper.readStream(in);
                }


            }catch (Exception ex)
            {
                Log.e("IOException",ex.toString());
            }

            return res;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            try{
                JSONArray jsonArray=new JSONArray(strings);
                colarr=new String[jsonArray.length()];
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    colarr[i]=jsonObject.getString("colname");
                }

            }catch (JSONException ex)
            {
                Log.e("ErrorJson",ex.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
    class UpdateDataAsync extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params)

        {
            String content="",res="",result="";
            HttpURLConnection httpURLConnection=null;
            try{
                content="Phone="+URLEncoder.encode(params[0],ActivityHelper.UTF)+
                        "&Address="+URLEncoder.encode(params[1],ActivityHelper.UTF)+
                        "&City="+URLEncoder.encode(params[2],ActivityHelper.UTF)+
                        "&College="+URLEncoder.encode(params[3],ActivityHelper.UTF)+
                        "&Email="+URLEncoder.encode(params[4],ActivityHelper.UTF);
            }catch (UnsupportedEncodingException ex)
            {
                Log.e("UpdateLog",ex.toString());
            }
            try{
                URL updateurl=new URL(UpdateDetails);
                httpURLConnection=(HttpURLConnection)updateurl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setChunkedStreamingMode(0);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=new BufferedOutputStream(httpURLConnection.getOutputStream());
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    InputStream in=new BufferedInputStream(httpURLConnection.getInputStream());
                    res=ActivityHelper.readStream(in);
                }else{
                    InputStream in=new BufferedInputStream(httpURLConnection.getErrorStream());
                    res=ActivityHelper.readStream(in);
                }

            }catch (IOException ex)
            {
                Log.e("IOUpdate",ex.toString());
            }
            try{
                JSONObject jsonObject=new JSONObject(res);
                if(jsonObject.has("result"))
                {
                    return  jsonObject.getString("result");
                }

            }catch (JSONException ex)
            {
                Log.e("JsonExUpdate",ex.toString());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            AlertDialog.Builder upcomplete=new AlertDialog.Builder(update.getContext());
            if(!s.equals(""))
            {
                progressDialog.dismiss();
                if(s.equals("OK"))
                {

                    upcomplete.setMessage("העדכון בוצע בהצלחה");
                    upcomplete.setNeutralButton("אישור", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog=upcomplete.create();
                    dialog.show();
                }
            }else{
                upcomplete.setMessage("העדכון נכשל נסה שנית מאוחר יותר");
                upcomplete.setNeutralButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog=upcomplete.create();
                dialog.show();
            }
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog=new ProgressDialog(update.getContext());
            progressDialog.setMessage("מתבצע עדכון נתונים נא המתן...");
            progressDialog.show();

        }
    }

    }





