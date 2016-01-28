package com.ipq.ipq.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ipq.ipq.Adapters.MainAdapterGrid;
import com.ipq.ipq.Model.Item;
import com.ipq.ipq.R;
import com.ipq.ipq.Session;

import java.util.ArrayList;

/**
 * Created by IG on 02/01/2016.
 */


    public  class Main_Menu extends android.support.v4.app.Fragment
{

        private GridView MainMenu;
        private View MenuView;
        private ArrayList<Item> itemArrayList=new ArrayList<Item>();
        int [] gridviewImages={R.drawable.driver,R.drawable.passenger,R.drawable.settings,R.drawable.shutdown};




        public Main_Menu()
        {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            MenuView=inflater.inflate(R.layout.fragment_main__menu_, container, false);
            MainMenu=(GridView)MenuView.findViewById(R.id.mainMenugrd);
            itemArrayList.add(new Item("תפריט נהג",gridviewImages[0]));
            itemArrayList.add(new Item("תפריט נוסע",gridviewImages[1]));
            itemArrayList.add(new Item("הגדרות כלליות",gridviewImages[2]));
            itemArrayList.add(new Item("יציאה",gridviewImages[3]));
            MainAdapterGrid mainAdapterGrid=new MainAdapterGrid(MenuView.getContext(),R.layout.grid_row,itemArrayList);
            MainMenu.setAdapter(mainAdapterGrid);
            MainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(position==2)
                    {
                       Fragment generalSettings=new GeneralSettings();
                        FragmentManager fragmentManager=getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_content,generalSettings).addToBackStack("GeneralSettings").commit();


                    }
                    if(position==3)
                    {
                        DialogExit();
                    }
                }
            });


            return MenuView;
        }
    private void DialogExit()
    {
        AlertDialog.Builder exit=new AlertDialog.Builder(MainMenu.getContext());
        exit.setTitle("התנתקות מהאפליקציה");
        exit.setIcon(R.drawable.ic_launcher);
        exit.setMessage("האם אתה בטוח שאתה רוצה להתנתק?");
        exit.setPositiveButton("כן",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final Session session=new Session(MenuView.getContext());
                session.LogOut();
                QuitApp();
            }
        });
        exit.setNegativeButton("לא",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        AlertDialog alert=exit.create();
        alert.show();

    }
    public  void QuitApp()
    {

        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);




    }
}
