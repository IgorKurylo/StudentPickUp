package com.ipq.ipq.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.Model.User;

import java.util.ArrayList;

/**
 * Class for Bookmarks Passengers and Drivers
 *
 */
public class DataBaseSqlite extends SQLiteOpenHelper
{

    private  static String BookM_Drivers="BookMarksDriver";
    private  static String BookM_Passengers="BookMarksStudents";
    private static  String UserIpq="UserIPQ";
    private static String IPQDB="IPQ.db";
    private static int Version=2;
    private String EmptyString="";
    public static String SQL_DELETE_USER="DROP TABLE IF EXISTS "+UserIpq;
    public DataBaseSqlite(Context context)
    {
        super(context, IPQDB, null, Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists  "+BookM_Drivers+"(ID_Dr Text,Name Text,Phone Text,DAddress Text,Dir Text)");
        db.execSQL("create table if not exists  "+BookM_Passengers+"(ID_Ps Text,Name Text,Phone Text,PAddress Text,Dir Text)");
        db.execSQL("create table if not exists  "+UserIpq+"(Phone Text ,FullName Text,Email Text ,City Text,Street Text,CollageName Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);

    }

}
