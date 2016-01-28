package com.ipq.ipq.Fragments;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.ipq.ipq.DataBaseSqlite;
import com.ipq.ipq.IpqMain;

import com.ipq.ipq.Model.UserDirecation;
import com.ipq.ipq.Model.UserPlace;
import com.ipq.ipq.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;


public class Update_Direcation extends Fragment {

    private View Direcation;
    public LatLng latLng;
    public UserPlace userPlace;
    public ProgressDialog progressDialog;
    public String UrlGoogle="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
    public String KeyApi="&key=";
    public String UrlAutoComplete="";
    RadioGroup userDir;
    EditText AddressWork,CityWork;
    AutoCompleteTextView NameCollage;
    public String[] listOfColleges=null;
    TextView titlework;
    public boolean dirindicator=false;
    UserDirecation userDirecation=null;
    int CollegeID,r=10;
    public String FullAddress="";
    Button update,back;
    ProgressBar progress;
    RadioButton Work,Collage;
    Handler autocomplete;
    public ArrayAdapter CollageComplete=null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Direcation=inflater.inflate(R.layout.update_direcation,container,false);
         userDir=(RadioGroup)Direcation.findViewById(R.id.RadioGrp);
         Work=(RadioButton)Direcation.findViewById(R.id.work);
        Collage=(RadioButton)Direcation.findViewById(R.id.collage);
        NameCollage=(AutoCompleteTextView)Direcation.findViewById(R.id.NameofCollage);
        titlework=(TextView)Direcation.findViewById(R.id.text_work);
        AddressWork=(EditText)Direcation.findViewById(R.id.btnAddress);
        CityWork=(EditText)Direcation.findViewById(R.id.City);
        update=(Button)Direcation.findViewById(R.id.updatedir);
        back=(Button)Direcation.findViewById(R.id.backbtn);
        progress=(ProgressBar)Direcation.findViewById(R.id.progressLoading);
        progress.setVisibility(View.GONE);
        UrlAutoComplete=getResources().getString(R.string.AutoCompleteCollege);
        return Direcation;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        userDir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.collage:
                        NameCollage.setVisibility(View.VISIBLE);
                        titlework.setVisibility(View.INVISIBLE);
                        AddressWork.setVisibility(View.INVISIBLE);
                        CityWork.setVisibility(View.INVISIBLE);
                        dirindicator=true;
                       // UserDirecation userDirCollege=new UserDirecation(CollegeID,NameCollage.getText().toString(),true);
                        //take the info from edittext for collage
                        break;
                    case R.id.work:
                        NameCollage.setVisibility(View.INVISIBLE);
                        titlework.setVisibility(View.VISIBLE);
                        AddressWork.setVisibility(View.VISIBLE);
                        CityWork.setVisibility(View.VISIBLE);
                        dirindicator=false;

                        // UserDirecation userDirWork=new UserDirecation(CityWork.getText().toString(),AddressWork.getText().toString(),false);
                        //take the info from edittext for work

                        break;
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack("Update_Direcation", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call to Async
                //check which RadioButton is checked and make the userDirecation object
                if(userDir.getCheckedRadioButtonId()==R.id.collage)
                {
                    FullAddress = NameCollage.getText().toString();
                }
                if(userDir.getCheckedRadioButtonId()==R.id.work)
                {

                    FullAddress=AddressWork.getText().toString()+","+CityWork.getText().toString();

                }
                if(NameCollage.getText().toString().equals(""))
                {
                    NameCollage.setError("יש להקליד מוסד לימודים");
                }
                if(AddressWork.getText().toString().equals("") && CityWork.getText().toString().equals("") )
                {
                    AddressWork.setError("יש להקליד כתובת עבודה לעדכון");
                    CityWork.setError("יש להקליד עיר עבודה לעדכון");
                }

                new LocationTask().execute(FullAddress);
                FullAddress="";



            }
        });
        NameCollage.setThreshold(2);
        NameCollage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(NameCollage.isPerformingCompletion())
                {
                    return;
                }
                if(s.length()<=1)
                {

                    return;
                }

                if(s.length()>=2)
                {
                    progress.setVisibility(View.VISIBLE);

                 new AutoCompleteCollegeTask().execute(s.toString());


                }




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class UpdateUserDirecation extends AsyncTask<UserDirecation,String, Integer>
    {


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer==1)
            {
                progressDialog.dismiss();
                Toast.makeText(Direcation.getContext(),"עודכן בהצלחה",Toast.LENGTH_LONG).show();
            }else{

                progressDialog.dismiss();
                Toast.makeText(Direcation.getContext(),"עדכון כשל..נסה שנית מאוחר יותר",Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Direcation.getContext());
            progressDialog.setCancelable(true);
            progressDialog.setMessage("מעדכן נתונים ...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(UserDirecation... params)
        {
            DataBaseSqlite sqLiteDatabase=new DataBaseSqlite(Direcation.getContext(),"DB_IPQ_BookMarks",null,1);

            boolean flag = params[0].isCollage();
            UserDirecation userDirecation = params[0];

            String Update = "",Address="",NameDir="";
            int result = 0;
            String content = "", res = "";
            if (flag) {
                try {

                    content = "CollegeName=" + URLEncoder.encode(userDirecation.getCollageName(), "UTF-8") +
                            "&IDCollege=" + URLEncoder.encode(String.valueOf(userDirecation.getCollegeID()), "UTF-8") +
                            "&ColAdd="+URLEncoder.encode(userDirecation.getCollageAddress(),"UTF-8")+
                            "&Lat=" + URLEncoder.encode(String.valueOf(userDirecation.getLatLng().latitude)) +
                            "&Lng=" + URLEncoder.encode(String.valueOf(userDirecation.getLatLng().longitude))+
                            "&userid="+URLEncoder.encode(String.valueOf(IpqMain.getId()));
                    Address=userDirecation.getCollageAddress();
                    NameDir=userDirecation.getCollageName();
                    Update=getResources().getString(R.string.UpdateDirecation);
                } catch (Exception ex) {
                    Log.e("URLENCODER", ex.toString());
                }

            }
            else{

                try {
                    content = "FullAdress=" + URLEncoder.encode(userDirecation.getWorkAddress(), "UTF-8") +
                            "&Lat=" + URLEncoder.encode(String.valueOf(userDirecation.getLatLng().latitude)) +
                            "&Lng=" + URLEncoder.encode(String.valueOf(userDirecation.getLatLng().longitude))+
                            "&userid="+URLEncoder.encode(String.valueOf(IpqMain.getId()));
                    Address=userDirecation.getWorkAddress();
                    Update=getResources().getString(R.string.UpdateWork);
                } catch (Exception ex)
                {
                    Log.e("URLENCODER", ex.toString());
                }

            }
            BufferedReader reader;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(Update);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setChunkedStreamingMode(0);
                //urlConnection.setRequestProperty("Content-Type: text/html;","charset=utf-8");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(content);
                wr.flush();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                res = GetResponseString(reader);
                urlConnection.disconnect();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.has("result")) {
                        if (jsonObject.getString("result").equals("OK")) {
                            result = 1;
                            if(sqLiteDatabase.CheckIFExits(String.valueOf(IpqMain.getId())))
                            {
                                sqLiteDatabase.UpdateDireaction(String.valueOf(IpqMain.getId()),Address,NameDir);
                            }else{
                                sqLiteDatabase.AddDirecation(String.valueOf(IpqMain.getId()), Address, NameDir);

                            }
                        }

                    }
                } catch (JSONException ex) {
                    Log.e("jsonexeption", ex.toString());
                    urlConnection.disconnect();

                }


            } catch (IOException ex) {
                Log.e("IOException", ex.toString());
                urlConnection.disconnect();


            }

            return result;
        }
    }
    class LocationTask extends AsyncTask<String,String,UserPlace>
    {
        @Override
        protected UserPlace doInBackground(String... params)
        {
            String Dir=params[0],KeyApi="&key=",UrlGoogle="";
            KeyApi+=getResources().getString(R.string.keyapigooglemaps);
            UrlGoogle=getResources().getString(R.string.urlgoogle);
            String response="";
            HttpURLConnection urlConnection=null;
            UrlGoogle+=URLEncoder.encode(Dir)+KeyApi;
            try {
                URL Getloc = new URL(UrlGoogle);
                urlConnection=(HttpURLConnection)Getloc.openConnection();
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());

                BufferedReader reader=new BufferedReader(in);
                response=GetResponseString(reader);
                urlConnection.disconnect();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    JSONObject jsonmapObjects=jsonArray.getJSONObject(0);
                    JSONObject geometry=jsonmapObjects.getJSONObject("geometry");
                    String address=jsonmapObjects.getString("formatted_address");
                    JSONObject location=geometry.getJSONObject("location");
                    latLng=new LatLng(Double.valueOf(location.getString("lat")),Double.valueOf(location.getString("lng")));
                    userPlace=new UserPlace(address,latLng);

                }catch (JSONException ex)
                {
                    Log.e("JsonError",ex.toString());
                    urlConnection.disconnect();
                }
            }catch (IOException ex)
            {
                Log.e("IoEception",ex.toString());
                urlConnection.disconnect();
            }
            finally {
                urlConnection.disconnect();
            }

            return userPlace;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Direcation.getContext());
            progressDialog.setCancelable(true);
            progressDialog.setMessage("מאתר מיקום לעדכון...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(UserPlace userPlace1) {
            super.onPostExecute(userPlace1);
            progressDialog.dismiss();
            if(dirindicator) {
                final Random random=new Random();
                CollegeID= random.nextInt(100)*r++; //generate by random number collageid
                userDirecation = new UserDirecation(CollegeID, NameCollage.getText().toString(),userPlace.getAddress(), true, userPlace.getLatLng());
                new UpdateUserDirecation().execute(userDirecation);
                //call to other async
            }else{
                userDirecation = new UserDirecation(FullAddress,false,latLng);
                new UpdateUserDirecation().execute(userDirecation);

            }

        }
    }
    public String GetResponseString(BufferedReader reader)
    {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }catch (IOException ex)
        {
            Log.e("Error",ex.toString());
        }
        return sb.toString();
    }

    class AutoCompleteCollegeTask extends AsyncTask<String,String[],String[]>
    {
        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            progress.setVisibility(View.GONE);
            CollageComplete=new ArrayAdapter(Direcation.getContext(),android.R.layout.simple_list_item_1,s);
            NameCollage.setAdapter(CollageComplete);
            NameCollage.showDropDown();
            CollageComplete.notifyDataSetChanged();

        }

        @Override
        protected String[] doInBackground(String... params)
        {
           String[] arraycollages=null;
            HttpURLConnection urlConnection=null;
            String response="",res2="";
            String Content="";
            try {
                Content= "prefixtxt=" + URLEncoder.encode(params[0]);
            }catch (Exception ex)
            {
                Log.e("Error",ex.toString());
            }
            try {
                URL Getloc = new URL(UrlAutoComplete);
                urlConnection = (HttpURLConnection) Getloc.openConnection();
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write( Content );
                wr.flush();
                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(in);
                response = GetResponseString(reader);
                urlConnection.disconnect();

            }catch (IOException ex)
            {
               Log.e("Error_Connection",ex.toString());
                urlConnection.disconnect();
            }
            try {
                JSONArray jsonArray = new JSONArray(response);
                arraycollages=new String[jsonArray.length()];
                for(int i=0;i<jsonArray.length();i++)
                {
                    arraycollages[i]=jsonArray.get(i).toString();
                }
            }catch (Exception ex)
            {
                Log.e("JsonError",ex.toString());
                urlConnection.disconnect();
            }
            return arraycollages;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }




}
