package com.ipq.ipq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.TokenIterator;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;


public class DriverFrag extends android.support.v4.app.Fragment {


    private View driver;
    public String add1="";
    GridView gridView;
    ViewPager viewPager;
    public final String COLLEGE="Snunit Street, Karmiel";
    public int ID;
    public String SelectPassengers="http://igortestk.netai.net/SelectPassengers.php";
    Handler CallBack;
    public ArrayList<Passenger> passengers=null;
    FragmentTransaction fragmentTransaction;
    private int[] imgd={R.drawable.newbtn1,R.drawable.newbtn2,R.drawable.routebtn,R.drawable.update};
    private static android.support.v4.app.FragmentManager fragmentManager;
    public DriverFrag()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CallBack=new Handler()
        {

            @Override
            public void handleMessage(Message msg) {

                if (msg.what == -1) {

                    Toast.makeText(driver.getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
                }
                Bundle bd = msg.getData();

                passengers = (ArrayList<Passenger>) bd.getSerializable("pass");
                if (passengers != null) {

                    Fragment Mp = new MyPassengers();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pass", (Serializable) passengers);
                    Mp.setArguments(bundle);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_mainlayout, Mp, "B");
                    fragmentTransaction.addToBackStack("MyPassengers");
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(driver.getContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }

        };

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
    {
        driver=inflater.inflate(R.layout.driver_frag,container,false);
        CustomGrid customGrid=new CustomGrid(imgd,driver.getContext());
        viewPager=(ViewPager)driver.findViewById(R.id.pager);
        gridView=(GridView)driver.findViewById(R.id.gridView);
        gridView.setAdapter(customGrid);
        ID=IpqMain.getId();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentManager=getFragmentManager();

                if(position==0) {

                    RegisterToRide Reg = new RegisterToRide();
                    // Store the Fragment in stack
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_mainlayout, Reg, "A");
                    fragmentTransaction.addToBackStack("RegisterToRide");
                    fragmentTransaction.commit();

                }
                if(position==1)
                {

                   DBQ.ShowMyPassenger(SelectPassengers,ID,CallBack);

                }
                if(position==2)
                {
                    add1=IpqMain.GetAdrss();
                    if(add1!=null) {
                        try {
                            if (passengers.size() > 0) {

                                Intent i = new Intent(driver.getContext(), Show_Map.class);
                                i.putExtra("ListPass", (Serializable) passengers);
                                i.putExtra("DriverAdd", add1);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(driver.getContext(), Show_Map.class);
                                i.putExtra("DriverAdd", add1);
                                i.putExtra("Collage", COLLEGE);
                                startActivity(i);

                            }
                        } catch (Exception e) {
                            Toast.makeText(driver.getContext(), "Check Passengers List", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(driver.getContext(),"Update Your Address And Try Again ",Toast.LENGTH_LONG).show();
                    }



                }
                if(position==3)
                {
                    UpdateData Ud=new UpdateData();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_mainlayout, Ud, "D");
                    fragmentTransaction.addToBackStack("UpdateData");
                    fragmentTransaction.commit();
                }
            }
        });
        return driver;
    }



}
