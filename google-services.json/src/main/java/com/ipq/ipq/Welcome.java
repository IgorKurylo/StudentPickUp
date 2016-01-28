package com.ipq.ipq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.Toast;




public class Welcome extends Activity
{

    public ProgressBar Pb;
    public  int progress=0;
    public ImageView ipq,car;
    public  int mProgressStatus=0;
    public int t,b,r,l;
    private static  int Time_OUT=2000;
    private Handler Handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //init the screen to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcomewindow); //set the content from xml file to screen
        ipq = (ImageView) findViewById(R.id.ipq); //init all item in the xml file
        car = (ImageView) findViewById(R.id.car);
        Pb=(ProgressBar)findViewById(R.id.progress);
         b=car.getBottom(); //take the size of the car pictures
        t=car.getTop();
        l=car.getLeft();
        r=car.getRight();
          final Session s=new Session(getApplicationContext()); //constructer to build seesion



        new Handler().postDelayed(new Runnable() {  //handler which work with animation
            @Override
            public void run() {


                    TranslateAnimation Ta = new TranslateAnimation(l, r+1000, t, b);  //init animation for car
                    Ta.setDuration(3000);
                    Ta.setRepeatCount(0);
                    Ta.setInterpolator(new AccelerateInterpolator());
                    car.startAnimation(Ta);

                Ta.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {  //end of animation check if the user login the
                                                                       // application pass him to main if not user need to login
                        if(s.IsLogIn()) {
                            Intent intent = new Intent(getApplicationContext(), IpqMain.class);
                            startActivity(intent);
                            finish();
                        }
                        if(!s.IsLogIn())
                        {
                            Intent intent = new Intent(getApplicationContext(), LoginWindow.class);
                            startActivity(intent);
                            finish();
                        }
                        }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        }, Time_OUT);   //time for waiting the car is go

    }







}
