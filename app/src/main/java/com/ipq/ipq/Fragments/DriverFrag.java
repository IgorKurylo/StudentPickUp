package com.ipq.ipq.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ipq.ipq.Adapters.CustomGrid;
import com.ipq.ipq.Adapters.gridviewAdapter;
import com.ipq.ipq.DBQ;
import com.ipq.ipq.IpqMain;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.R;
import com.ipq.ipq.Show_Map;

import java.io.Serializable;
import java.util.ArrayList;


public class DriverFrag extends android.support.v4.app.Fragment {


    private View driver;
    public String add1="";
    ProgressBar openning;
    GridView gridView;
    ViewPager viewPager;
    public String Direction="";
    public final String COLLEGE="Snunit Street, Karmiel";
    public int ID;
    public String ShowPassengers="";
    public String DeleteDrivers="http://igortestk.netai.net/DeleteDriver.php";
    Handler CallBack,DeleteCallBack;
    public ArrayList<Passenger> passengers=null;
    FragmentTransaction fragmentTransaction;
    private int[] imgd={R.drawable.newbtn1,R.drawable.newbtn2,R.drawable.routebtn,R.drawable.favirotebtn};
    private static android.support.v4.app.FragmentManager fragmentManager;
    public String[] direcations={"עבודה","בית","מוסד לימודים"};
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

                    Toast.makeText(driver.getContext(), "לא נמצאו נוסעים", Toast.LENGTH_LONG).show();
                    Fragment Mp = new MyPassengers();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_mainlayout, Mp, "B");
                    fragmentTransaction.addToBackStack("MyPassengers");
                    fragmentTransaction.commit();
                }else {
                    Bundle bd = msg.getData();
                    passengers = (ArrayList<Passenger>) bd.getSerializable("pass");
                    if (passengers != null)
                    {

                        Fragment Mp = new MyPassengers();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("pass", (Serializable) passengers);
                        Mp.setArguments(bundle);
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.fragment_mainlayout, Mp, "B");
                        fragmentTransaction.addToBackStack("MyPassengers");
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(driver.getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
                    }
                }

            }

        };
        DeleteCallBack=new Handler(){


            @Override
            public void handleMessage(Message msg) {

                if(msg.what==1)
                {
                    Toast.makeText(driver.getContext(),"צא לדרך! דרך צלחה",Toast.LENGTH_LONG).show();
                }

            }
        };

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
    {
        driver=inflater.inflate(R.layout.driver_frag,container,false);
        CustomGrid customGrid=new CustomGrid(imgd,driver.getContext());
        openning=(ProgressBar)driver.findViewById(R.id.opening);
        openning.setVisibility(View.INVISIBLE);
        openning.setVisibility(View.GONE);
        ShowPassengers=getResources().getString(R.string.ShowP);
        viewPager=(ViewPager)driver.findViewById(R.id.pager);
        gridView=(GridView)driver.findViewById(R.id.gridView);
        gridView.setAdapter(customGrid);
        ID= IpqMain.getId();
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

                    final AlertDialog.Builder list_of_direcation = new AlertDialog.Builder(driver.getContext());

                    list_of_direcation.setIcon(R.drawable.ic_launcher);
                    list_of_direcation.setTitle("בחר נוסעים לפי כיוון נסיעה");
                    final View list=inflater.inflate(R.layout.direcationlist,null);
                    list_of_direcation.setView(list);
                    ListView dir = (ListView) list.findViewById(R.id.list_dir);
                    list_of_direcation.setCancelable(true);
                    ArrayAdapter<String>  adapter = new ArrayAdapter<String>(list.getContext(),android.R.layout.simple_list_item_1,direcations);
                    dir.setAdapter(adapter);
                    final AlertDialog dialog=list_of_direcation.create();
                    dialog.show();

                    //alert
                    dir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Direction=direcations[position];

                            DBQ.ShowMyPassenger(ShowPassengers, ID, Direction, CallBack);
                            dialog.dismiss();
                        }
                    });
                }
                if(position==2)
                {

                    if(passengers!=null) {
                        add1 = IpqMain.GetAdrss();
                        if (add1 != null) {
                            if (passengers.size() > 0) {

                                Intent i = new Intent(driver.getContext(), Show_Map.class);
                                i.putExtra("ListPass", (Serializable) passengers);
                                i.putExtra("DriverAdd", add1);
                                startActivity(i);
                            }
                        }
                    }
                    else{
                       AlertDialog.Builder dialog=new AlertDialog.Builder(driver.getContext());
                        dialog.setMessage("לא נמצאו נוסעים ,הינך מעוניין לצאת לדרך ללא נוסעים?");
                        dialog.setIcon(R.drawable.ic_launcher);
                        dialog.setTitle("שים לב!");
                        dialog.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               //delete from drivers
                               // DBQ.DeleteFunc(String.valueOf(ID),DeleteDrivers,DeleteCallBack);


                            }
                        });
                        dialog.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(driver.getContext(),"המתן לנוסעים!",Toast.LENGTH_LONG).show();


                            }
                        });


                        dialog.show();
                    }
                }

                if(position==3)
                {
                    Fav_Passengers FP=new Fav_Passengers();
                    FragmentManager fragmentManager=getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_mainlayout,FP,"E").addToBackStack("Fav_Passengers").commit();
                }
            }
        });
        return driver;
    }



}
