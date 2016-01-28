package com.ipq.ipq;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.ipq.ipq.Model.Passenger;

import java.util.ArrayList;


public class IpqMain extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener  {

    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    android.support.v7.app.ActionBar actionBar;
    private static android.support.v4.app.FragmentManager fragmentManager;
    private String User="",CurrentUser="";
    private int ID;
    private static int ID_User;
    private static Passenger passenger;
    private static String AddrsDriver;
    private Session session;
    private ArrayList<String> user;
    AlertDialog.Builder alertDialog;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipq_main);
        session=new Session(getApplicationContext());
        if(session.IsLogIn())
        {
            ID=Integer.parseInt(session.getUserDetails().get(2));
            ID_User=ID;
            User=session.getUserDetails().get(0);
            AddrsDriver=session.getUserDetails().get(3);
        }
        else {

            Intent login=new Intent(getApplicationContext(),LoginWindow.class);
            startActivity(login);
            finish();

        }
//        Bundle bd=getIntent().getExtras();
//        if(bd!=null)
//        {
//            Toast.makeText(getApplicationContext(),"ID"+bd.getString("NofID"),Toast.LENGTH_LONG).show();
//        }
        viewPager=(ViewPager)findViewById(R.id.pager);
        user=session.getUserDetails();
        CurrentUser=user.get(1);
        actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


        if (actionBar != null) {
            actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);
        }
            actionBar.addTab(actionBar.newTab().setText("נהגים").setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText("תפריט ראשי").setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText("נוסעים").setTabListener(this));
            actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        tabsAdapter=new TabsAdapter(getSupportFragmentManager());
        actionBar.setDisplayShowHomeEnabled(false);

        viewPager.setAdapter(tabsAdapter);
        viewPager.setCurrentItem(1,false);
        actionBar.setSelectedNavigationItem(1);
        getSupportActionBar().setTitle("תפריט ראשי");
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
              getSupportActionBar().setSelectedNavigationItem(position);
                getSupportActionBar().setTitle(tabsAdapter.getPageTitle(position));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public static  int getId()
    {

        return ID_User;
    }

    public static String GetAdrss()
    {
        return AddrsDriver;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
//            MenuInflater menuInflater=getMenuInflater();
//            menuInflater.inflate(R.menu.menu_main,menu);

                   return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

                return super.onOptionsItemSelected(item);

    }


    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

       viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
    @Override
    protected void onStop() {
        super.onStop();
//        session.SaveLoginDetails(user.get(0),user.get(1),ID);

    }
    @Override
    protected void onPause() {
        super.onPause();
//        session.SaveLoginDetails(user.get(0),user.get(1),ID);


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp=new AlertDialog.Builder(IpqMain.this);
        exitApp.setIcon(R.drawable.ic_launcher);
        exitApp.setTitle("יציאה");
        exitApp.setMessage("אתה עומד לצאת מהאפליקציה האם אתה בטוח?");
        exitApp.setCancelable(false);
        exitApp.setPositiveButton("כן ", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                finish();
                System.exit(1);




            }
        });
        exitApp.setNegativeButton("לא,אמשיך לחפש טרמפ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        exitApp.show();
    }


}
