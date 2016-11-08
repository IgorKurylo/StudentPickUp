package com.ipq.ipq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.GpsStatus;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ipq.ipq.DataBase.DataBaseSqlite;
import com.ipq.ipq.Utils.ActivityHelper;
import com.ipq.ipq.Utils.GPSActivity;
import com.ipq.ipq.Utils.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Main_Menu extends Fragment implements OnMapReadyCallback,GpsStatus.Listener
{
    String UserAddres = "ISRAEL",UserName;
    public LatLng userLatLng = null;
    public LatLng DefaultLocation=new LatLng(31.000,35.000);
    public int REQUEST_CODE=1;
    public MapView mapView;
    public View view;
    private static GoogleMap mMap;
    public Session session;
    public DataBaseSqlite db;
    public GPSBroadcast gpsBroadcast;
    public GPSActivity gpsActivity;
    public FrameLayout frameLayout;
    public Main_Menu() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      //  view=inflater.inflate(R.layout.fragment_main__menu_,container,false);
        mapView = (MapView)view.findViewById(R.id.map);
       // frameLayout=(FrameLayout)view.findViewById(R.id.content_map);
        gpsActivity=new GPSActivity(view.getContext(),getActivity());
        gpsBroadcast=new GPSBroadcast();
        IntentFilter filter = new IntentFilter("android.location.GPS_ENABLED_CHANGE");
        filter.addAction(ActivityHelper.GPSFIXLOC);
        filter.addAction(ActivityHelper.ACTIONGPS);
        getActivity().registerReceiver(gpsBroadcast,filter);
        db=new DataBaseSqlite(view.getContext());
        session=new Session(view.getContext());
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if(gpsActivity.GetLocationUser()!=null)
        {
            userLatLng=gpsActivity.GetLocationUser();
            try
            {
//                UserAddres=db.GetFullAddress(session.GetDetUser(ActivityHelper.Email_User));
//                UserName=db.GetFullNameUser(session.GetDetUser(ActivityHelper.Email_User));
                if(UserAddres.equals(""))
                {
                    ShowDialogUpdate();
                }
                //update the map
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            userLatLng=DefaultLocation;
            //update the map
            ShowDialogOpenGps();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==0)
        {
            if(gpsActivity.isProviderEnabled())
            {
                if(userLatLng!=null)
                {
                     UpdateMap(userLatLng);
                }else{
                    userLatLng=gpsActivity.GetLocationUser();
                    if(userLatLng!=null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UpdateMap(userLatLng);

                            }
                        });
                    }
                }
            }
        }

    }
    public void UpdateMap(LatLng latLng)
    {

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title(UserName).snippet(UserName));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(
                        latLng).zoom(10).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        if(googleMap!=null)
        {

            mMap=googleMap;
            googleMap.addMarker(new MarkerOptions().position(userLatLng).title(UserName).snippet(UserName));
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
            {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker)
                {
                    View v=getActivity().getLayoutInflater().inflate(R.layout.info_window_layout,null,false);
                    TextView name=(TextView)v.findViewById(R.id.title);
                    TextView address=(TextView)v.findViewById(R.id.address);
                    name.setText(UserName);
                    address.setText(UserAddres);
                    return v;
                }

            });
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,10.0f));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
    public void ShowDialogOpenGps()
    {
        AlertDialog.Builder dialog=new AlertDialog.Builder(view.getContext());
        dialog.setMessage(getString(R.string.gpsnotworking));
        dialog.setPositiveButton("הפעל", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent gpsIntent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(gpsIntent,REQUEST_CODE);

            }
        });
        dialog.setNegativeButton("לא תודה", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }
    public void ShowDialogUpdate()
    {
        AlertDialog.Builder dialog=new AlertDialog.Builder(view.getContext());
        dialog.setMessage(getString(R.string.needupdate));
        dialog.setPositiveButton("עדכן", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //create dialog for add adress
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("מאוחר יותר", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
        if(gpsBroadcast!=null) {
            getActivity().registerReceiver(gpsBroadcast, new IntentFilter(ActivityHelper.ACTIONGPS));
            getActivity().registerReceiver(gpsBroadcast, new IntentFilter(ActivityHelper.GPSFIXLOC));

        }
    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
        mapView.onDestroy();
        getActivity().unregisterReceiver(gpsBroadcast);

    }

    @Override
    public void onLowMemory()
    {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onGpsStatusChanged(int event)
    {
        switch (event)
        {
            case GpsStatus.GPS_EVENT_STARTED:
                Toast.makeText(view.getContext(),"GPS_EVENT_STARTED",Toast.LENGTH_LONG).show();
            break;
            case GpsStatus.GPS_EVENT_STOPPED:
                Toast.makeText(view.getContext(),"GPS_EVENT_STOPPED",Toast.LENGTH_LONG).show();

                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Toast.makeText(view.getContext(),"GPS_EVENT_SATELLITE_STATUS",Toast.LENGTH_LONG).show();

                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                Toast.makeText(view.getContext(),"GPS_EVENT_FIRST_FIX",Toast.LENGTH_LONG).show();
                userLatLng=gpsActivity.GetLocationUser();
                System.out.print("Loc->"+userLatLng.latitude+" :"+userLatLng.longitude);

                break;
        }

    }

//    class LocationTask extends AsyncTask<String,String,UserPlace>
//    {
//        @Override
//        protected UserPlace doInBackground(String... params)
//        {
//            String Dir=params[0],KeyApi="&key=",UrlGoogle="";
//            KeyApi+=getResources().getString(R.string.keyapigooglemaps);
//            UrlGoogle=getResources().getString(R.string.urlgoogle);
//            String response="";
//            HttpURLConnection urlConnection=null;
//            UrlGoogle+= URLEncoder.encode(Dir)+KeyApi;
//            UserPlace userPlace=null;
//            try {
//                URL Getloc = new URL(UrlGoogle);
//                urlConnection=(HttpURLConnection)Getloc.openConnection();
//                urlConnection.setChunkedStreamingMode(0);
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setDoOutput(true);
////                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
////
////                BufferedReader reader=new BufferedReader(in);
////                response= ActivityHelper.readStream(reader);
//                urlConnection.disconnect();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray=jsonObject.getJSONArray("results");
//                    JSONObject jsonmapObjects=jsonArray.getJSONObject(0);
//                    JSONObject geometry=jsonmapObjects.getJSONObject("geometry");
//                    String address=jsonmapObjects.getString("formatted_address");
//                    JSONObject location=geometry.getJSONObject("location");
////                    latLng=new LatLng(Double.valueOf(location.getString("lat")),Double.valueOf(location.getString("lng")));
////                    userPlace=new UserPlace(address,latLng);
//
//                }catch (JSONException ex)
//                {
//                    Log.e("JsonError",ex.toString());
//                    urlConnection.disconnect();
//                }
//            }catch (IOException ex)
//            {
//                Log.e("IoEception",ex.toString());
//                if(urlConnection!=null) {
//                    urlConnection.disconnect();
//                }
//            }
//            finally {
//                if(urlConnection!=null) {
//                    urlConnection.disconnect();
//                }
//            }
//
//            return userPlace;
//        }
//
//
//    }

    public  class GPSBroadcast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            if(intent.getAction().equals(ActivityHelper.ACTIONGPS))
            {
                if(gpsActivity.isProviderEnabled())
                {
                    userLatLng = gpsActivity.GetLocationUser();
                    if(userLatLng!=null)
                    {
                        UpdateMap(userLatLng);
                    }
                    Toast.makeText(context,"GPS_ON",Toast.LENGTH_LONG).show();
                }else
                {

                    Toast.makeText(context,"GPS_OFF",Toast.LENGTH_LONG).show();


                }


            }
            if(intent.getAction().equals(ActivityHelper.GPSFIXLOC))
            {
                userLatLng=gpsActivity.GetLocationUser();

            }
        }
    }
}
