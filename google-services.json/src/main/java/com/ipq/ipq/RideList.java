package com.ipq.ipq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RideList extends android.support.v4.app.Fragment {


    ListView Ride_List;
    ArrayList<String> DriversList=new ArrayList<String>(1);
    String ID_driver;
    String date;
    Passenger passenger;
    View Back;
    ProgressBar savingdriver;
    public View list;
    String idPass;
    public ArrayList<Driver> drivers=null;
    Handler callb;
    public String AddPassQ="http://igortestk.netai.net/AddPassenger.php";
    public RideList() {

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
                    Toast.makeText(list.getContext(),"You Added Successfully to Drive",Toast.LENGTH_LONG).show();
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

        try {
            Bundle bd=getArguments();
            drivers = (ArrayList<Driver>) bd.getSerializable("driver");
            date=bd.getString("date");


            for(int i=0;i<drivers.size();i++)
            {
                DriversList.add(drivers.toString()+"\nDate :"+date);

            }
            String D=DriversList.get(0).substring(1,51);
            String D2=DriversList.get(0).substring(53,68);
            DriversList.add(D+D2);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(list.getContext(), R.layout.passagerlist, R.id.pass,DriversList);
            adapter.notifyDataSetInvalidated();
            Ride_List.setAdapter(adapter);//create the list
            Ride_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ID_driver=drivers.get(position).getIdD();
                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("רישום לטרמפ ");
                    alert.setMessage("רוצה להירשם לנהג זה?");

                    alert.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {



                            DBQ.AddPass(ID_driver,idPass,callb,AddPassQ);
                            savingdriver.setVisibility(View.VISIBLE);


                        }
                    });

                    alert.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getFragmentManager().popBackStack("SearchRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    });

                    alert.show();





                }
            });
        }catch (Exception e)
        {
            Toast.makeText(list.getContext(),"No Drivers Post Driving",Toast.LENGTH_LONG).show();
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack("SearchRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return list;

    }


}
