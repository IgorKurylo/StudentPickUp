package com.ipq.ipq.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ipq.ipq.Adapters.MainAdapterGrid;
import com.ipq.ipq.Model.Item;
import com.ipq.ipq.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralSettings extends android.support.v4.app.Fragment{

    private GridView GS;
    private MainAdapterGrid settingsAdapter;
    private View GeneralSettings;
    private ArrayList<Item> itemArrayList=new ArrayList<Item>();
    private int[] generalImages={R.drawable.up,R.drawable.addlocations,R.drawable.addusergrp,R.drawable.back};
    private static android.support.v4.app.FragmentManager fragmentManager;
    public String fragname;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemArrayList.add(new Item("עדכון פרטים",generalImages[0]));
        itemArrayList.add(new Item("עדכון יעד נסיעה",generalImages[1]));
        itemArrayList.add(new Item("יצירת קבוצת נסיעה",generalImages[2]));
        itemArrayList.add(new Item("חזור לתפריט ראשי",generalImages[3]));
        settingsAdapter=new MainAdapterGrid(getActivity(),R.layout.grid_row,itemArrayList);
    }

    public GeneralSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        GeneralSettings= inflater.inflate(R.layout.fragment_general_settings, container, false);
        fragmentManager=getFragmentManager();
        return GeneralSettings;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        GS=(GridView)view.findViewById(R.id.genaralSettings);

        GS.setAdapter(settingsAdapter);
        GS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                android.support.v4.app.Fragment fragment=null;
                switch (position)
                {
                    case 0:
                        fragment = new UpdateData();
                        fragname="UpdateData";
                        break;
                    case 1:
                        fragment=new Update_Direcation();
                        fragname="Update_Direcation";
                        break;
                    case 3:
                        getFragmentManager().popBackStack("GeneralSettings", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        break;



                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.genaralSettings_content, fragment);
                fragmentTransaction.addToBackStack(fragname);
                fragmentTransaction.commit();



            }
        });


    }
}
