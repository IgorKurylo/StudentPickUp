package com.ipq.ipq.Fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ipq.ipq.Adapters.CustomGrid;
import com.ipq.ipq.R;


public class PassengerFragment extends android.support.v4.app.Fragment {

    View passenger;
    GridView gridView;
    FragmentTransaction fragmentTransaction;
    private int[] imgd={R.drawable.searchbtn,R.drawable.favoratpasbtn};
    private static android.support.v4.app.FragmentManager fragmentManager;
    public PassengerFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
    {
       passenger=inflater.inflate(R.layout.pass_frag,container,false);
        CustomGrid customGrid=new CustomGrid(imgd,passenger.getContext());
        gridView=(GridView)passenger.findViewById(R.id.gridView);
        gridView.setAdapter(customGrid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                fragmentManager=getFragmentManager();
                if(position==0)
                {
                    SearchRide SR=new SearchRide();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_mainlayout1, SR, "E");
                    fragmentTransaction.addToBackStack("SearchRide");
                    fragmentTransaction.commit();

                }

                if(position==1)
                {
                    Fav_Drivers FD=new Fav_Drivers();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_mainlayout1, FD);
                    fragmentTransaction.addToBackStack("Fav_Drivers");
                    fragmentTransaction.commit();

                }
            }
        });

        return passenger;
    }
}
