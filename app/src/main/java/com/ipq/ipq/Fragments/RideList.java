package com.ipq.ipq.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ipq.ipq.Adapters.MainAdapterGrid;
import com.ipq.ipq.DBQ;
import com.ipq.ipq.IpqMain;
import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.Model.Item;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.R;
import com.ipq.ipq.Session;
import com.parse.ParsePush;

import java.util.ArrayList;


public class RideList extends android.support.v4.app.Fragment {


    ListView Ride_List;
    ArrayList<String> DriversList=new ArrayList<String>(1);
    ArrayList<String> Tmplist=new ArrayList<String>(1);
    String ID_driver;
    String Direcation;
    Passenger passenger;
    View Back;
    ProgressBar savingdriver;
    public View list;
    String idPass;
    public   int flagAlreadyReg=-1;
    public ArrayList<Driver> drivers=null;
    Handler callb;
    private String AddPassesngers="";
    public RideList()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callb=new Handler(){


            @Override
            public void handleMessage(Message msg) {
                if(msg.what==-1)
                {
                    Toast.makeText(list.getContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                    savingdriver.setVisibility(View.GONE);
                }
                if(msg.what==1)
                {
                    Toast.makeText(list.getContext(),"התווספת בהצלחה לנהג זה",Toast.LENGTH_LONG).show();
                    ParsePush push = new ParsePush();
                    push.setChannel("Drivers");
                    push.setMessage("התווסף לך נוסע");
                    push.sendInBackground();
                    savingdriver.setVisibility(View.GONE);
                    savingdriver.setVisibility(View.INVISIBLE);
                }
            }
        };

    }
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = inflater.inflate(R.layout.ride_list, container, false);
        Ride_List = (ListView) list.findViewById(R.id.listViewRide);//create list strucre
        Back=(View)list.findViewById(R.id.backbtn);
        savingdriver=(ProgressBar)list.findViewById(R.id.saving);
        savingdriver.setVisibility(View.GONE);
        idPass=String.valueOf(IpqMain.getId());
        AddPassesngers=getResources().getString(R.string.AddP);
        try {
            Bundle bd=getArguments();
            drivers = (ArrayList<Driver>) bd.getSerializable("driver");
            for(int i=0;i<drivers.size();i++)
            {
                DriversList.add(drivers.get(i).toString());

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(list.getContext(), R.layout.passagerlist, R.id.pass,DriversList);
            adapter.notifyDataSetInvalidated();
            Ride_List.setAdapter(adapter);//create the list
            Ride_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    ID_driver = drivers.get(position).getIdD();
                    Direcation=drivers.get(position).getDir();
                    if(Integer.parseInt(ID_driver)!=IpqMain.getId())
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                        alert.setTitle("רישום לטרמפ ");
                        alert.setMessage("רוצה להירשם לנהג זה?");

                        if(flagAlreadyReg!=position) {
                            alert.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

//check how many passenger added to this driver
                                    DBQ.AddPass(ID_driver, idPass, callb, Direcation, AddPassesngers);
                                    savingdriver.setVisibility(View.VISIBLE);
                                    flagAlreadyReg = position;


                                }
                            });

                            alert.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    getFragmentManager().popBackStack("SearchRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                }
                            });

                            alert.show();
                        }else{
                            Toast.makeText(list.getContext(),"הינך רשום לנסיעה זאת",Toast.LENGTH_LONG).show();
                        }

                    }else{

                        Toast.makeText(list.getContext(),"אינך יכול להירשם לנסיעה זאת",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(list.getContext(),"אף נהג לא פירסם נסיעה",Toast.LENGTH_LONG).show();
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack("SearchRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        return list;

    }

    //Function which help build string for show the drivers list
    public String BuildNewString(String str)
    {
        int index,index2;
        String newstr="";
        index=str.indexOf("[");
        index2=str.indexOf("]");
        newstr=str.substring(index+1,index2);
        return newstr;

    }


    /**
     * Main menu
     */




}
