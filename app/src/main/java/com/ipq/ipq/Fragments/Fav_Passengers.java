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
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.R;

import java.util.ArrayList;


public class Fav_Passengers extends android.support.v4.app.Fragment {
    View Fav_View;
    ListView listView;
    Button back;
    CheckBox fav;
    public ArrayList<Passenger> passengers=null;
    public DataBaseSqlite db;
    ArrayAdapter<String> adapter=null;
    public ArrayList<String> Fav_Pass=new ArrayList<String>();
    public Fav_Passengers()
    {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState)
    {
        Fav_View=inflater.inflate(R.layout.favorite_pass,container,false);
        listView=(ListView)Fav_View.findViewById(R.id.fav_pass);
        listView.setFocusable(false);
        back=(Button)Fav_View.findViewById(R.id.backbtn);
        //  Fav_Pass.clear();
        //adapter=(ArrayAdapter<String>)listView.getAdapter();
       db=new DataBaseSqlite(Fav_View.getContext(),"DB_IPQ_BookMarks",null,1);
        try {

            passengers = db.SelectAllPassengers();
            for (int i=0;i<passengers.size();i++ )
            {
                Fav_Pass.add(passengers.get(i).toString());
            }

        }catch (Exception e)
        {
            Log.e("DBLiteException:",e.toString());
        }
        adapter=new ArrayAdapter<String>(Fav_View.getContext(), R.layout.custom_favlist, R.id.text_pass,Fav_Pass);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final CheckBox checkBox=(CheckBox)view.findViewById(R.id.fav_icon);
                checkBox.setChecked(false);



                 if(!checkBox.isChecked()) {
                     db.RemoveBookM(passengers.get(position));
                     Fav_Pass.remove(position);
                     adapter.notifyDataSetChanged();
                     Toast.makeText(view.getContext(), "נוסע נמחק מרשימת המועדפים", Toast.LENGTH_LONG).show();
                 }

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack("Fav_Passengers", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        return Fav_View;
    }
}
