package com.ipq.ipq;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ipq.ipq.Utils.ActivityHelper;
import com.ipq.ipq.Utils.Session;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginWindow extends AppCompatActivity
{

    private EditText Email, Pass;
    public TextView IPQ;
    public TextInputLayout emailadd,pass;
    private Button Sign,PassResetbtn, Signup;
    public  String user_email = "", user_pass = "", Login = "",Phone;
    Session session=null;
    public  boolean flag = true;
    private FragmentManager fragmentManager;
    public ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Email = (EditText) findViewById(R.id.email);
        Pass = (EditText) findViewById(R.id.pass);
        Sign = (Button) findViewById(R.id.signin);
        IPQ=(TextView)findViewById(R.id.titlelogin);
        Typeface type= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/arkitech.ttf");
        IPQ.setTypeface(type);

        Login=getResources().getString(R.string.Login);
        session=new Session(getApplicationContext());
        PassResetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordReset Pr=new PasswordReset();
                fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.reset_frag,Pr).addToBackStack("").commit();

            }
        });
        Sign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (ActivityHelper.CheckConnenction(getApplicationContext()))
                {
                    user_email = Email.getText().toString().trim();
                    user_pass = Pass.getText().toString().trim();
                    if (user_email.equals(""))
                    {
                        Email.setError("נא למלא אימייל");
                        flag = false;
                    }
                    if (user_pass.equals("")) {
                        Pass.setError("נא למלא סיסמה");
                        flag = false;
                    }

                    if (flag)
                    {
                        new LoginAsync().execute(user_email,user_pass);

                    }


                }
            }


        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent signup = new Intent(v.getContext(), SignUp.class);
                startActivity(signup);
            }
        });



    }
    class LoginAsync extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
//            try{
//                progressDialog.dismiss();
//                JSONObject jsonObject=new JSONObject(s);
//                if(jsonObject.has("result"))
//                {
//                 if(jsonObject.getString("result").equals("OK"))
//                 {
//                     Intent intent=new Intent(getApplicationContext(),IpqMain.class);
//                     session.SaveLoginDetails(user_email,user_pass);
//                     overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                     startActivity(intent);
//                     finish();
//                 }
//                 else
//                 {
//                     AlertDialog.Builder builder=new AlertDialog.Builder(LoginWindow.this);
//                     builder.setMessage("אימייל או הסיסמה אינם נכונים");
//                     builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
//                         @Override
//                         public void onClick(DialogInterface dialog, int which)
//                         {
//
//                             dialog.dismiss();
//
//                         }
//                     });
//                     AlertDialog faild=builder.create();
//                     faild.show();
//
//                 }
//                }
//            }catch (JSONException ex)
//            {
//                Log.e("JsonLogin",ex.toString());
//            }
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if(Build.VERSION.SDK_INT>=21)
            {

                progressDialog=new ProgressDialog(LoginWindow.this,R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getString(R.string.loginauth));
                progressDialog.show();

            }
            else
            {
                progressDialog=new ProgressDialog(LoginWindow.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getString(R.string.loginauth));
                progressDialog.show();

            }
        }

        @Override
        protected String doInBackground(String... params)
        {
            String content="",res="";
            HttpURLConnection httpURLConnection=null;
            try{
                content="Email="+ URLEncoder.encode(params[0],ActivityHelper.UTF)+
                        "&Pass="+URLEncoder.encode(params[1],ActivityHelper.UTF);

            }catch (UnsupportedEncodingException ex)
            {
                Log.e("Login",ex.toString());
            }
            try
            {
                URL login=new URL(Login);
                httpURLConnection=(HttpURLConnection)login.openConnection();
                httpURLConnection.setChunkedStreamingMode(0);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=new BufferedOutputStream(httpURLConnection.getOutputStream());
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
                    res=ActivityHelper.readStream(inputStream);

                }else{
                    InputStream in=new BufferedInputStream(httpURLConnection.getErrorStream());
                    res=ActivityHelper.readStream(in);
                }
            }catch (IOException ex)
            {
                Log.e("Error",ex.toString());
            }
            return res;
        }
    }



    @Override
    public void onBackPressed()
    {

        finish();

    }


}



























