package com.ipq.ipq;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class Show_Map extends FragmentActivity implements OnMapReadyCallback {
    //class which show map to driver , show the direction from his address via points of passengers to distention.

    private GoogleMap map;
    double x,y;
    String Dis,Time;
    Button btn1;
    public final String DIRECATION="ORT Braude College of Engineering";
    public  List<LatLng> Points=null;

    Handler callBack;
    String adddriver,add2,add1;
    ArrayList<Passenger> listofPasseengers=null;
    String [] Address;
    PolylineOptions polylineOptions=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_map);
        btn1=(Button)findViewById(R.id.showdirecation);
        Intent mapdata=getIntent();
        listofPasseengers=(ArrayList<Passenger>)mapdata.getSerializableExtra("ListPass");
        adddriver=mapdata.getStringExtra("DriverAdd");
        Address=new String[listofPasseengers.size()+2];
        Address[0]=adddriver;

        for(int i=0;i<listofPasseengers.size();i++)
        {
              Address[i+1]=listofPasseengers.get(i).getAddress();
        }
        Address[listofPasseengers.size()+1]=DIRECATION;
        callBack=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                Bundle bd=msg.getData();

                Dis=bd.getString("Distance");
                Time=bd.getString("Time");
                Points=(List<LatLng>)bd.getSerializable("LatLngs");
                Toast.makeText(getApplicationContext(),"Distance: "+Dis+""+"Time"+Time,Toast.LENGTH_LONG).show();

                map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(Points.get(0), 0));
                map.animateCamera(CameraUpdateFactory.zoomTo(12));


                if(map!=null)
                {
                    for(int i=0;i<Points.size();i++)
                    {
                        if(i==0) {
                            map.addMarker(new MarkerOptions().position(Points.get(i)).title("Driver"));
                        }
                        if(i==Points.size()-1)
                        {
                            map.addMarker(new MarkerOptions().position(Points.get(i)).title("College"));
                        }




                    }
                    polylineOptions=new PolylineOptions();
                    polylineOptions.addAll(Points);
                    polylineOptions.color(Color.RED);
                    polylineOptions.width(7);
                    map.addPolyline(polylineOptions);


                }
            }
        };
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalcShortRoad.ShowWay(Address, callBack);
            }
        });




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


    }
}
