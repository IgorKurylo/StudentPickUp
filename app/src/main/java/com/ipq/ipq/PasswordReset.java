package com.ipq.ipq;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;


public class PasswordReset extends Fragment {

    public final int TAG_Email=1;
    public final int TAG_Password=2;
    public boolean isResetPass=false;

    public HttpURLConnection httpURLConnection=null;
    public ProgressDialog progressDialog=null;
    public BufferedReader reader=null;
    public AlertDialog.Builder popup=null;
    private Button verifyEmail,Resetpass;
    private EditText email,code,pass;
    private int Cod=0,CodeEnter=0;
    public String UrlResetPassword="";
    private int NotificationNum=0;
    private NotificationManager myNotificationManager;
    public Random rdm;
    private String emailadress="",newpass="",message="";
    View Preset;
    public PasswordReset() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        Preset=inflater.inflate(R.layout.fragment_password_reset, container, false);
        email=(EditText)Preset.findViewById(R.id.email);
        code=(EditText)Preset.findViewById(R.id.code);
        pass=(EditText)Preset.findViewById(R.id.newpassword);
        verifyEmail=(Button)Preset.findViewById(R.id.verifyemail);
        Resetpass=(Button)Preset.findViewById(R.id.reset);
        rdm=new Random();
        SetDisable_Enable(code,false);
        SetDisable_Enable(pass,false);
        UrlResetPassword=getResources().getString(R.string.ResetPassword);




        return Preset;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((email.getText().toString().equals("")))
                {
                   email.setError("יש להכניס אימייל");
                }else if(!(email.getText().toString().contains("@")))
                {
                   email.setError("אימייל לא תקין");
                }
                else{
                    emailadress = email.getText().toString().trim();
                    message=getResources().getString(R.string.text2);
                    new ResetPassword().execute(emailadress, String.valueOf(TAG_Email));

                }

            }
        });
        Resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(email.getText().toString().equals(""))
                {
                    email.setError("יש להכניס אימייל");
                }else if(pass.getText().toString().equals(""))
                {
                    pass.setError("יש להכניס סיסמה לאיפוס");

                }else{
                    message=getResources().getString(R.string.text3);

                    emailadress=email.getText().toString().trim();
                    newpass=pass.getText().toString().trim();
                    new ResetPassword().execute(emailadress,String.valueOf(TAG_Password),newpass);

                }


            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }
    public void SetDisable_Enable(EditText editText,boolean en_ds)
    {

        editText.setEnabled(en_ds);
        editText.setFocusable(en_ds);
        editText.setFocusableInTouchMode(en_ds);
        editText.setClickable(en_ds);
        editText.setText(editText.getText(), TextView.BufferType.NORMAL);
    }

    class ResetPassword extends AsyncTask<String,String,Integer>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Preset.getContext());
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.setCancelable(true);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer==1)
            {
                progressDialog.dismiss();
                SetDisable_Enable(code,true);
                SetDisable_Enable(pass,true);

                Cod=rdm.nextInt(6000);
                popup=new AlertDialog.Builder(Preset.getContext());
                popup.setMessage("משתמש אומת בהצלחה , מיד תקבל את קוד אימות להמשך איפוס הסיסמה");
                popup.setCancelable(false);
                popup.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CodeNotification(Preset.getContext(),Cod);
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog=popup.create();
                popup.show();

            }
            if(integer==2)
            {
                progressDialog.dismiss();
                popup=new AlertDialog.Builder(Preset.getContext());
                popup.setMessage("סיסמתך אופסה בהצלחה");
                popup.setPositiveButton("אישור",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(Preset.getContext(), LoginWindow.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();


                    }
                });
                AlertDialog dialog=popup.create();
                popup.show();



            }
        }

        @Override
        protected Integer doInBackground(String... params)
        {

            String str="";
            int result=0;
            String Content="";
            String email=params[0];
            String tag=params[1];
            if(params.length>=3) {
                String npass = params[2];
                Content="email_user="+URLEncoder.encode(email)+"&tag="+URLEncoder.encode(String.valueOf(tag))+"&npass="+URLEncoder.encode(npass);


            }
            else{
                Content="email_user="+URLEncoder.encode(email)+"&tag="+URLEncoder.encode(String.valueOf(tag));
            }
            String response="";


            try {
                URL vemailurl = new URL(UrlResetPassword);
                httpURLConnection=(HttpURLConnection)vemailurl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty( "Connection", "close" );
                httpURLConnection.setChunkedStreamingMode(0);
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(Content);
                wr.flush();
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    response = GetResponseString(reader);
                }
                if(httpURLConnection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                {
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                    response = GetResponseString(reader);
                }
                httpURLConnection.disconnect();
               try{

                   JSONObject jsonObject=new JSONObject(response);

                   if(jsonObject.getString("result").equals("OK"))
                   {
                       return  1;
                   }
                   if(jsonObject.getString("result").equals("Success"))
                   {
                       return 2;
                   }

               }catch (JSONException ex)
               {
                   Log.e("ErrorJson",ex.toString()+"  "+response);

                   httpURLConnection.disconnect();
               }

            }catch (IOException ex)
            {
                Log.e("Err0r",ex.toString());
                httpURLConnection.disconnect();
            }finally {
                httpURLConnection.disconnect();

            }

            return  result;
        }
    }


    public String GetResponseString(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException ex) {
            Log.e("Error", ex.toString());
        }
        return sb.toString();
    }
    public void CodeNotification(Context context,long Code)
    {
        String msg="קוד האימות שלך הוא:"+String.valueOf(Code);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder verifycode=new Notification.Builder(context);
        verifycode.setSmallIcon(R.drawable.ic_launcher);
        verifycode.setContentTitle("IPQ - איפוס סיסמה");
        verifycode.setContentText(msg);
        //verifycode.setNumber(++NotificationNum);
        verifycode.setAutoCancel(true);
        verifycode.setSound(soundUri);
        Intent intent=new Intent(context.getApplicationContext(),PasswordReset.class);
//        intent.putExtra("NofID",NotificationNum);
       TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
      stackBuilder.addParentStack(LoginWindow.class);
      stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_ONE_SHOT);
       verifycode.setContentIntent(resultPendingIntent);
        myNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(NotificationNum,verifycode.build());
    }
}
