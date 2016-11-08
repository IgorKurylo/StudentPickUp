package com.ipq.ipq.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ipq.ipq.LoginWindow;
import com.ipq.ipq.MainActivity;

import java.util.ArrayList;


public class Session  {

    private   SharedPreferences login;
    private  SharedPreferences.Editor editor;
    private Context context;
    int PRIVATE_MODE=0;
    private static final String Email_User="Email";
    private static final String Pass_User="Pass";
    private static final String Addrs_User="Address";
    private static final String IsDriverToday="isDriver";
    private static final String ID_User="ID";
    private static final String LogIn="SuccesLog";



    public Session(Context c)
    {
        this.context=c;
        login=context.getSharedPreferences("IPQSession",PRIVATE_MODE);
        editor=login.edit();
        editor.apply();

    }
    public void SaveLoginDetails(String email,String pass)
    {
        editor.putBoolean(LogIn,true);
        editor.putString(Email_User, email);
        editor.putString(Pass_User,pass);
        editor.commit();


    }

    public boolean IsLogIn(){return login.getBoolean(LogIn,false);}
    public void LogOut()
    {
        editor.clear();
        editor.commit();
        Intent tologin=new Intent(context,LoginWindow.class);
        tologin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tologin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(tologin);
    }

    public void CheckLogin()
    {
        if(!this.IsLogIn())
        {
            Intent check=new Intent(context,MainActivity.class);
            check.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            check.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        }
    }

    public String GetDetUser(String key)
    {

        return login.getString(key,"");
    }

}
