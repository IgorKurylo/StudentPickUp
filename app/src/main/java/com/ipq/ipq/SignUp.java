package com.ipq.ipq;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.ipq.ipq.DataBase.DataBaseSqlite;
import com.ipq.ipq.Model.User;
import com.ipq.ipq.Utils.ActivityHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignUp extends AppCompatActivity
{
    public  EditText ConfirmPass, Email, Pass, FirstName, LastName,Phone;
    public TextInputLayout emailadd,passw,passc,Fname,Lname,PhoneNumber;
    public String  emailuser = "", passuser = "",Phonenum,pass1="",pass2="",SignUpUrl="",DateNow;
    public Button Submit;
    public LatLng latLng;
    public User user=null;
    public TextView IPQ;
    public  ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        FindViews();
        Typeface type= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/arkitech.ttf");
        IPQ.setTypeface(type);
        SignUpUrl=getResources().getString(R.string.SignUp);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                     pass1=Pass.getText().toString();
                     pass2=ConfirmPass.getText().toString();

                     if(Email.getText().toString().equals(""))
                    {
                      Email.setError("נא למלא אימייל");
                    }else
                    if(FirstName.getText().toString().equals(""))
                    {
                        FirstName.setError("יש למלא שם פרטי");
                    }else if(LastName.getText().toString().equals(""))
                    {
                        LastName.setError("יש למלא שם משפחה");
                    }
                    else if(Pass.getText().toString().equals(""))
                    {
                        Pass.setError("נא למלא סיסמה");
                    }
                    else if(!isSamePassword(pass1,pass2))
                    {
                        Pass.setError("סיסמאות לא תואמות");
                    }
                    else if(Phone.getText().toString().length()<10)
                    {
                        Phone.setError("מספר טלפון לא תקין");
                    }

                    else
                    {
                        emailuser = Email.getText().toString();
                        passuser = Pass.getText().toString();
                        Phonenum=Phone.getText().toString();
                    }


            }
        });

    }
    public void FindViews()
    {

        Email = (EditText) findViewById(R.id.Email);
        Pass = (EditText) findViewById(R.id.Pass);
        ConfirmPass = (EditText) findViewById(R.id.PassConfirm);
        FirstName = (EditText) findViewById(R.id.Fn);
        LastName = (EditText) findViewById(R.id.Ln);
        Submit = (Button) findViewById(R.id.submit);
        Phone=(EditText)findViewById(R.id.Phone);
        IPQ=(TextView)findViewById(R.id.ipqtext);
        emailadd=(TextInputLayout)findViewById(R.id.emailadd);
        Fname=(TextInputLayout)findViewById(R.id.Fname);
        Lname=(TextInputLayout)findViewById(R.id.Lname);
        passw=(TextInputLayout)findViewById(R.id.password);
        passc=(TextInputLayout)findViewById(R.id.passcon);
        PhoneNumber=(TextInputLayout)findViewById(R.id.phoneNumber);
        Email.setFocusable(true);

    }
    public boolean isSamePassword(String pass1,String pass2)
    {
        return pass1.equals(pass2);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }






    @Override
    protected void onResume() {
        super.onResume();
    }
}









