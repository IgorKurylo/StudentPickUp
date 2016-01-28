package com.ipq.ipq;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TimerTask;

public class LoginWindow extends ActionBarActivity
{

    EditText Email, Pass;
    ImageView LoginImg;
    public String AddressDriver;
    ImageButton Sign;
    public String Name,Phone;
    final String SELECTUSER = "http://igortestk.netai.net/SelectbyID.php";
    public  String user_email = "", user_pass = "";
    public int ID;
    public  boolean flag = true;
    public ProgressBar progressBar;
    Handler callBack;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Email = (EditText) findViewById(R.id.email);
        Pass = (EditText) findViewById(R.id.pass);
        Sign = (ImageButton) findViewById(R.id.signin);
        LoginImg = (ImageView) findViewById(R.id.loginimg);
        progressBar=(ProgressBar)findViewById(R.id.loading);
        progressBar.setVisibility(View.INVISIBLE);
        final Intent login = getIntent();
        final Session session=new Session(getApplicationContext());
         getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        callBack = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bd=msg.getData();
                if(msg.what==-1)
                {
                    Toast.makeText(getApplicationContext(), "Email Or Password Is Faild", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Sign.setClickable(true);


                }else {

                    Name = msg.getData().getString("FullName");
                    ID=msg.getData().getInt("id");
                    AddressDriver=msg.getData().getString("FullAdd");
                    Phone=msg.getData().getString("phone");
                    Passenger currentPass=new Passenger(Name,AddressDriver,Phone);
                    session.SaveLoginDetails(user_email,user_pass,ID,AddressDriver);
                    Intent logintomain = new Intent(getApplicationContext(), IpqMain.class);

                    logintomain.putExtra("id",ID);
                    logintomain.putExtra("FullName",Name);
                    logintomain.putExtra("fulladd",AddressDriver);
                    logintomain.putExtra("CurPass",(Serializable)currentPass);
                    startActivity(logintomain);
                    progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                }


            }
        };
        Sign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                user_email = Email.getText().toString();
                user_pass = Pass.getText().toString();
                if (user_email.equals("") || user_pass.equals("")) {
                    Toast.makeText(v.getContext(), "Must Fill all Fields", Toast.LENGTH_LONG).show();
                    flag = false;
                }
                if (flag)
                {
                        DBQ.LoginUser(user_email, user_pass, SELECTUSER, callBack);
                        session.SaveLoginDetails(user_email,user_pass,ID,AddressDriver);

                        v.setClickable(false);
                        progressBar.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);

                }


                }


        });


    }





    @Override
    public void onBackPressed() {
        Intent back=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();

    }
}



























