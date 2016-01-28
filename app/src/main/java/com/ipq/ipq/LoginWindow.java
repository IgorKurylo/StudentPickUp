package com.ipq.ipq;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ipq.ipq.Model.Passenger;
import com.parse.ParsePush;

import java.io.Serializable;

public class LoginWindow extends ActionBarActivity
{

    EditText Email, Pass;
    ImageView LoginImg;
    boolean driver_yes_no=false;
    public String AddressDriver;
    Button Sign;
    public String Name,Phone,userdir,namecol;
    private String Login = "";
    public  String user_email = "", user_pass = "";
    public int ID;
    public  boolean flag = true;
    public ProgressBar progressBar;
    Handler callBack;
    DataBaseSqlite sqlite=null;
    CheckBox checkdriver;
    private int NotificationNum=0;
    private NotificationManager myNotificationManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Email = (EditText) findViewById(R.id.email);
        Pass = (EditText) findViewById(R.id.pass);
        Sign = (Button) findViewById(R.id.signin);

        progressBar=(ProgressBar)findViewById(R.id.loading);
        progressBar.setVisibility(View.INVISIBLE);
        checkdriver=(CheckBox)findViewById(R.id.driver);
        Login=getResources().getString(R.string.Login);
        getSupportActionBar().setTitle("כניסה");
        getSupportActionBar().setIcon(R.drawable.login_icon);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Intent login = getIntent();
        final Session session=new Session(getApplicationContext());
         getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        callBack = new Handler() {
            @Override
            public void handleMessage(Message msg) {
               // Bundle bd=msg.getData();
                if(msg.what==-1)
                {
                    Email.setError("יש לבדוק את תקינות הכתובת הדואר האלקטרונית");
                    Pass.setError("יש לבדוק את תקינות הסיסמה");
                    progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Sign.setClickable(true);
                }else {
                    Name = msg.getData().getString("FullName");
                    ID=msg.getData().getInt("id");
                    AddressDriver=msg.getData().getString("FullAdd");
                    Phone=msg.getData().getString("phone");
                    Passenger currentPass=new Passenger(Name,AddressDriver,Phone,null,String.valueOf(ID));
                    session.SaveLoginDetails(user_email,user_pass,ID,AddressDriver);
                    Intent logintomain = new Intent(getApplicationContext(), IpqMain.class);
                    logintomain.putExtra("id",ID);
                    logintomain.putExtra("FullName",Name);
                    logintomain.putExtra("fulladd",AddressDriver);
                    logintomain.putExtra("CurPass",(Serializable)currentPass);
                    TriggerNotification(getApplicationContext());
                    startActivity(logintomain);
                    progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    //call notification for login
                    finish();
                }

            }
        };
        Sign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                user_email = Email.getText().toString();
                user_pass = Pass.getText().toString();
                if (user_email.equals("") ) {
                    Email.setError("נא למלא אימייל");
                    flag = false;
                }
                if(user_pass.equals(""))
                {
                    Pass.setError("נא למלא סיסמה");
                    flag = false;
                }
                if(checkdriver.isChecked())
                {
                    driver_yes_no=true;
                }
                if (flag)
                {
                    if(driver_yes_no)
                    {
                        ParsePush.subscribeInBackground("Drivers");

                    }else {

                        ParsePush.unsubscribeInBackground("Drivers");

                    }
                        DBQ.LoginUser(user_email, user_pass, Login, callBack);
                        session.SaveLoginDetails(user_email,user_pass,ID,AddressDriver);


                        v.setClickable(false);
                        progressBar.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);

                }


                }


        });


    }



    public void TriggerNotification(Context context)
    {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder loginnotif=new Notification.Builder(context);
        loginnotif.setSmallIcon(R.drawable.ic_launcher);
        loginnotif.setContentTitle("התחברת ל-IPQ");
        loginnotif.setContentText("לחץ כאן לפתיחת האפליקציה");
        loginnotif.setNumber(++NotificationNum);
        loginnotif.setAutoCancel(true);
        loginnotif.setSound(soundUri);
        Intent intent=new Intent(context.getApplicationContext(),IpqMain.class);
        intent.putExtra("NofID",NotificationNum);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(IpqMain.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_ONE_SHOT);
        loginnotif.setContentIntent(resultPendingIntent);
        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(NotificationNum,loginnotif.build());



    }


    @Override
    public void onBackPressed()
    {
        Intent back=new Intent(getApplicationContext(),MainActivity.class);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

        startActivity(back);
        finish();

    }


}



























