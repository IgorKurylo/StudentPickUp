package com.ipq.ipq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.Parse;
import com.parse.ParseInstallation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.os.StrictMode.ThreadPolicy;


public class MainActivity extends ActionBarActivity {

    Button login,Signup;
    View Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=21)
        {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }


        getSupportActionBar().setTitle("ברוכים הבאים");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Logo=(View)findViewById(R.id.logoipg);
        login = (Button) findViewById(R.id.logbtn);
        Signup = (Button) findViewById(R.id.signbtn);
        final Session session=new Session(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               try {

                   Intent Login = new Intent(getApplicationContext(), LoginWindow.class);
                   startActivity(Login);
                   overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


               }catch (Exception e)
               {
                   Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_LONG).show();
               }


            }

        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent signup = new Intent(v.getContext(), SignUp.class);
                    startActivity(signup);
                }catch (Exception e)
                {
                    Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Logout)
         {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
