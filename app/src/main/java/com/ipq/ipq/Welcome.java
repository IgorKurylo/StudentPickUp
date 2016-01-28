package com.ipq.ipq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
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

import com.parse.Parse;


public class Welcome extends Activity
{

    public ProgressBar Pb;
    public  int progress=0;
    public ImageView ipq,car,back;
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
//        back=(ImageView)findViewById(R.id.background);
//        Display display=getWindowManager().getDefaultDisplay();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        BitmapFactory.decodeResource(getResources(), R.drawable.road2edit, options);
//        int imageHeight = options.outHeight;
//        int imageWidth = options.outWidth;
//        String imageType = options.outMimeType;
//        back.setImageBitmap(ImageRezize.decodeSampledBitmapFromResource(getResources(),R.drawable.road2edit,imageWidth,imageHeight));
        if(Build.VERSION.SDK_INT>=21)
        {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }

        ipq = (ImageView) findViewById(R.id.ipq); //init all item in the xml file
        car = (ImageView) findViewById(R.id.car);
//       car.setVisibility(View.INVISIBLE);
//        ipq.setVisibility(View.INVISIBLE);

        Pb=(ProgressBar)findViewById(R.id.progress);
         b=car.getBottom(); //take the size of the car pictures
        t=car.getTop();
        l=car.getLeft();
        r=car.getRight();



        final Session s=new Session(getApplicationContext()); //constructer to build seesion



        new Handler().postDelayed(new Runnable() {  //handler which work with animation
            @Override
            public void run() {

                try {
                    TranslateAnimation Ta = new TranslateAnimation(l, r + 1000, t, b);  //init animation for car
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
                            if (s.IsLogIn()) {
                                Intent intent = new Intent(getApplicationContext(), IpqMain.class);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                finish();
                            }
                            if (!s.IsLogIn()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                finish();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }catch (Exception e)
                {
                    Log.e("Error-Here:->", e.toString());
                }

            }
        }, Time_OUT);   //time for waiting the car is go

    }







}
