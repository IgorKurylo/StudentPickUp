package com.ipq.ipq;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipq.ipq.Activities.Drivers_Activity;
import com.ipq.ipq.Utils.Session;

import org.w3c.dom.Text;


public class Welcome extends AppCompatActivity
{



    public ImageView wheel;
    public TextView AppName,Slogan;
    private static final  int Time_OUT=2500;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //init the screen to full screen
        setContentView(R.layout.welcomewindow); //set the content from xml file to screen
        final Session s=new Session(getApplicationContext()); //constructer to build seesion
        wheel=(ImageView)findViewById(R.id.wheel);
        AppName=(TextView)findViewById(R.id.ipqtext);
        Slogan=(TextView)findViewById(R.id.slogan);
        Typeface type= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/arkitech.ttf");
        AppName.setTypeface(type);
        Slogan.setTypeface(type);
        Animation rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        wheel.startAnimation(rotate);
        new Handler().postDelayed(new Runnable() {  //handler which work with animation
            @Override
            public void run()
            {

                            Intent tmp=new Intent(getApplicationContext(), Drivers_Activity.class);
                            startActivity(tmp);




//                        if (s.IsLogIn())
//                        {
//                            Intent intent = new Intent(getApplicationContext(), IpqMain.class);
//                            startActivity(intent);
//                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                            finish();
//                        }
//                        if (!s.IsLogIn())
//                        {
//                            Intent intent = new Intent(getApplicationContext(), LoginWindow.class);
//                            startActivity(intent);
//                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                            finish();
//                        }
                    }



        }, Time_OUT);   //time for waiting the car is go

    }







}
