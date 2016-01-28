package com.ipq.ipq;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;


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
//        Intent intent=getIntent();
//        passenger=(Passenger)intent.getSerializableExtra("Curpass");


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


        Toast.makeText(getApplicationContext(),"Welcome User: "+User ,Toast.LENGTH_LONG).show();


        viewPager=(ViewPager)findViewById(R.id.pager);

        alertDialog=new AlertDialog.Builder(getApplicationContext());
        user=session.getUserDetails();
        CurrentUser=user.get(1);
        actionBar=getSupportActionBar();

        if (actionBar != null) {
            actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);
        }
        ActionBar.TabListener tabListener= new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

            actionBar.addTab(actionBar.newTab().setText("Driver").setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText("Passenger").setTabListener(this));



        tabsAdapter=new TabsAdapter(getSupportFragmentManager());
        actionBar.setDisplayShowHomeEnabled(false);
        viewPager.setAdapter(tabsAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              getSupportActionBar().setSelectedNavigationItem(position);

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
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.ipq_menu,menu);
        MenuItem item=menu.findItem(R.id.Useritem);
        item.setTitle(User);
            return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


//        int id=item.getItemId();
//        if(id==R.id.Useritem)
//        {
//          item.setTitle(User);
//        }
        switch (item.getItemId())
        {

            case  R.id.Logout:
                session.LogOut();
                Toast.makeText(getApplicationContext(),"Good Bye",Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.home:
                super.onBackPressed();



            default:
                return super.onOptionsItemSelected(item);
        }
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
        Intent backMain=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(backMain);
        finish();


    }


}
