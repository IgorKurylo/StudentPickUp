package com.ipq.ipq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.Model.Passenger;

import java.util.ArrayList;

/**
 * Class for Bookmarks Passengers and Drivers
 *
 */
public class DataBaseSqlite extends SQLiteOpenHelper
{

    private  static String BookM_Drivers="BookMarksDriver";
    private  static String BookM_Passengers="BookMarksStudents";
    private static  String UserDirecation="User_Direcation";

    public DataBaseSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists  "+BookM_Drivers+"(ID_Dr Text,Name Text,Phone Text,DAddress Text,Dir Text)");
        db.execSQL("create table if not exists  "+BookM_Passengers+"(ID_Ps Text,Name Text,Phone Text,PAddress Text,Dir Text)");
        db.execSQL("create table if not exists  "+UserDirecation+"(ID Text ,UserAddress Text,NameDirection Text )");



    }

    public void RemoveBookM(Object Role)
    {
        String query;
        SQLiteDatabase db=getWritableDatabase();
        if(Role instanceof Driver)
        {
            query="Delete from "+BookM_Drivers+" where Phone='"+((Driver)Role).getMobile()+"'";
            db.execSQL(query);
        }
        if(Role instanceof Passenger)
        {
            query="Delete from "+BookM_Passengers+" where Phone='"+((Passenger)Role).getPhone()+"'";
            db.execSQL(query);

        }

    }

    public void AddDirecation(String Idpassenger,String Address,String NameDirecation)
    {

        SQLiteDatabase db=getWritableDatabase();
        ContentValues cn= new ContentValues();
        cn.put("ID",Idpassenger);
        cn.put("UserAddress",Address);
        cn.put("NameDirection",NameDirecation);
        db.insert(UserDirecation,null,cn);
        db.close();

    }
    public ArrayList<String> GetDirecation() throws Exception
    {
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<String>();
        String query="Select * from "+UserDirecation;
        Cursor cr=db.rawQuery(query,null);
        if(cr==null)
        {
            throw  new Exception("Error in query");
        }
        else{

              arrayList.add(cr.getString(1));
              arrayList.add(cr.getString(2));

        }
        cr.close();
        return arrayList;
    }
    public boolean CheckIFExits(String ID)
    {

        String query="Select * from "+UserDirecation+" where ID='"+ID+"'";
        SQLiteDatabase db=getReadableDatabase();
        Cursor check=db.rawQuery(query,null);
        if(check.getCount()<=0)
        {
            return false;
        }
       return true;


    }
    public int UpdateDireaction(String id,String Address,String NameDirecation)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.put("UserAddress",Address);
        cn.put("NameDirection",NameDirecation);
        return db.update(UserDirecation,cn,"ID_Pass = ? ",new String[]{id});


    }

    public void AddBookM(Object Role)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cn= new ContentValues();

        if(Role instanceof Driver)
        {
            cn.put("Name",((Driver)Role).getFullName());
            cn.put("ID_Dr",((Driver)Role).getIdD());
            cn.put("Phone",((Driver)Role).getMobile());
            cn.put("DAddress",((Driver)Role).getAdress());
            cn.put("Dir",((Driver)Role).getDir());
            db.insert(BookM_Drivers,null,cn);
            db.close();
        }
        if(Role instanceof Passenger)
        {
            cn.put("Name",((Passenger)Role).getFullName());
            cn.put("ID_Ps",((Passenger)Role).getIDPass());
            cn.put("Phone",((Passenger)Role).getPhone());
            cn.put("PAddress",((Passenger)Role).getAddress());
            cn.put("Dir",((Passenger)Role).getDirecation());
            db.insert(BookM_Passengers,null,cn);
            db.close();
        }

    }
    public ArrayList<Passenger> SelectAllPassengers() throws Exception
    {
        String Name,Id,Phone,Address,query,Dir;
        ArrayList<Passenger> passengers=new ArrayList<Passenger>();
        SQLiteDatabase db=getWritableDatabase();
        query="Select * from "+BookM_Passengers;
        Cursor cr=db.rawQuery(query, null);
        if(cr==null)
        {
            throw new Exception("Error in Query");
        }
        if(cr.moveToFirst())
        {
            do{

                Name=cr.getString(1);
                Id=cr.getString(0);
                Phone=cr.getString(2);
                Address=cr.getString(3);
                Dir=cr.getString(4);
                passengers.add(new Passenger(Name,Address,Phone,Dir,Id));
            }while(cr.moveToNext());
        }
        cr.close();
        return passengers;
    }
    public ArrayList<Driver> SelectAllDrivers() throws Exception
    {
        String Name,Id,Phone,Address,query,Dir;
        ArrayList<Driver> Drivers=new ArrayList<Driver>();
        SQLiteDatabase db=getWritableDatabase();
        query="Select * from "+BookM_Drivers;
        Cursor cr=db.rawQuery(query, null);
        if(cr==null)
        {
            throw new Exception("Error in Query");
        }
        if(cr.moveToFirst())
        {
            do{

                Name=cr.getString(1);
                Id=cr.getString(0);
                Phone=cr.getString(2);
                Address=cr.getString(3);
                Dir=cr.getString(4);
                Drivers.add(new Driver(Name,Id,"",Phone,Address, "","",Dir ));
            }while(cr.moveToNext());
        }
        cr.close();
        return Drivers;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

}
