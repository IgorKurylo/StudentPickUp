package com.ipq.ipq.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.ipq.ipq.DataBaseSqlite;
import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.R;

import java.util.ArrayList;


public class Fav_Drivers extends android.support.v4.app.Fragment {
    View Fav_View;
    ListView listView;
    Button back;
    CheckBox fav;
    public ArrayList<Driver> drivers=null;
    public DataBaseSqlite db;
    ArrayAdapter<String> adapter=null;
    public ArrayList<String> Fav_drivers=new ArrayList<String>();
    public Fav_Drivers()
    {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState)
    {
        Fav_View=inflater.inflate(R.layout.favorite_drivers,container,false);
        listView=(ListView)Fav_View.findViewById(R.id.fav_pass);
        listView.setFocusable(false);
        back=(Button)Fav_View.findViewById(R.id.backbtn);
        //  Fav_Pass.clear();
        //adapter=(ArrayAdapter<String>)listView.getAdapter();
       db=new DataBaseSqlite(Fav_View.getContext(),"DB_IPQ_BookMarks",null,1);
        try {
            drivers = db.SelectAllDrivers();
            for (int i=0;i<drivers.size();i++ )
            {
                Fav_drivers.add(drivers.get(i).toString());
            }

        }catch (Exception e)
        {
            Log.e("DBLiteException:",e.toString());
        }
        adapter=new ArrayAdapter<String>(Fav_View.getContext(), R.layout.custom_favlist, R.id.text_pass,Fav_drivers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final CheckBox checkBox=(CheckBox)view.findViewById(R.id.fav_icon);
                checkBox.setChecked(false);



                 if(!checkBox.isChecked()) {
                     db.RemoveBookM(drivers.get(position));
                     Fav_drivers.remove(position);
                     adapter.notifyDataSetChanged();
                     Toast.makeText(view.getContext(), "נהג נמחק מרשימת המועדפים", Toast.LENGTH_LONG).show();
                 }

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack("Fav_Drivers", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        return Fav_View;
    }
}
