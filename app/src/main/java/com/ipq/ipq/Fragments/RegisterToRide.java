package com.ipq.ipq.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ipq.ipq.DBQ;
import com.ipq.ipq.IpqMain;
import com.ipq.ipq.Model.Post;
import com.ipq.ipq.R;
import com.ipq.ipq.Session;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.util.ArrayList;
import java.util.Calendar;


public class RegisterToRide extends android.support.v4.app.Fragment{

    public View Register;
    private int ID;
    Button btnDate, btnTimeOut,btnSubmit,btnRate;

    private String PostDriving="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    public  String date="",hourOut="",homeOrCollage="",rate="",id;
    RadioGroup radioCollagrHomeGroup;
    RadioButton CollageOrHome,collage,home;
    ProgressBar posting;
    public Post driving=null;
    public Session s;
    public int code=0;
    public ArrayList<String> info;
    Handler callback;
    public View Back;
    private static android.support.v4.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public RegisterToRide()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1)
                {
                    posting.setVisibility(View.GONE);
                    Toast.makeText(Register.getContext(),"Driving Added Successfully",Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack("RegisterToRide",FragmentManager.POP_BACK_STACK_INCLUSIVE);



                }
                if(msg.what==-1)
                {
                    Toast.makeText(Register.getContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                    posting.setVisibility(View.GONE);
                    posting.setVisibility(View.INVISIBLE);

                }

            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        Register=inflater.inflate(R.layout.register_to_ride,container,false);
        btnTimeOut = (Button) Register.findViewById(R.id.btnTimeOut);
        btnDate= (Button)Register.findViewById(R.id.btnDate);
        btnSubmit= (Button)Register.findViewById(R.id.submit);
        btnRate = (Button) Register.findViewById(R.id.btnRate);
        posting=(ProgressBar)Register.findViewById(R.id.posting);
        Back=(View)Register.findViewById(R.id.backbtn);
        posting.setVisibility(View.INVISIBLE);
        PostDriving=getResources().getString(R.string.AddD);
        ID= IpqMain.getId();
        btnDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(Register.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date=""+year + "-"+ (monthOfYear + 1) + "-" + +dayOfMonth;

                                date=year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth;


                            }
                        }, mYear, mMonth, mDay);

                dpd.show();
            }
            });


        btnTimeOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(Register.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                //Toast.makeText(getApplicationContext(),hourOfDay + ":" + minute,Toast.LENGTH_LONG).show();
                                hourOut=hourOfDay + ":" + minute;
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }
        });
        //init the radio buttons
        collage=(RadioButton)Register.findViewById(R.id.collage);
        home=(RadioButton)Register.findViewById(R.id.home);
        radioCollagrHomeGroup=(RadioGroup)Register.findViewById(R.id.rdGrpCollageHome);
        radioCollagrHomeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected

                if(checkedId == R.id.collage) {
                    homeOrCollage="Collage";
                    Toast.makeText(Register.getContext(), homeOrCollage,
                            Toast.LENGTH_SHORT).show();

                }
                if (checkedId == R.id.home) {
                    homeOrCollage="Home";
                    Toast.makeText(Register.getContext(),homeOrCollage,
                            Toast.LENGTH_SHORT).show();

                }
                if(collage.isChecked())
                {
                    homeOrCollage="Collage";
                }
                if(home.isChecked())
                {
                    homeOrCollage="Home";
                }
            }
        });
        //launch the dialog with rate
        btnRate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(Register.getContext());
                alert.setTitle("הגדר תעריף");
                alert.setMessage("הכנס את תעריף הנסיעה");

                // Set an EditText view to get user input
                final EditText input = new EditText(Register.getContext());
                alert.setView(input);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(Register.getContext(), input.getText().toString() + "ש''ח",
                                Toast.LENGTH_SHORT).show();
                        rate = input.getText().toString();
                        // Do something with value!
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        //when click submit check the all fields if filled
        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rate.equals("") && homeOrCollage.equals("") && date.equals(""))
                {
                    Toast.makeText(v.getContext(), "נא למלא את כל הפרטים לגבי הפירסום", Toast.LENGTH_LONG).show();
                }else if(rate.equals(""))
                {
                    Toast.makeText(v.getContext(), "נא למלא דמי השתתפות לנסיעה זו", Toast.LENGTH_LONG).show();
                }
                else if(date.equals(""))
                {
                    Toast.makeText(v.getContext(), "נא למלא תאריך נסיעה", Toast.LENGTH_LONG).show();

                }
                else if(homeOrCollage.equals(""))
                {
                    Toast.makeText(v.getContext(), "נא למלא יעד נסיכה", Toast.LENGTH_LONG).show();

                }
                else {

                    try {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Register.getContext());
                        alert.setTitle("פירסום טרמפ");
                        alert.setMessage("לפרסם את הטרמפ?");
                        alert.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                driving = new Post(ID, date, hourOut, homeOrCollage, rate);

                                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                                installation.put("user", ID);
                                installation.saveInBackground();
                                ParsePush.subscribeInBackground("Drivers");
                                DBQ.AddDriving(PostDriving, driving, callback);


                                posting.setVisibility(View.GONE);
                                posting.setVisibility(View.VISIBLE);
                            }
                        });
                        alert.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getFragmentManager().popBackStack("RegisterToRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                        });

                        alert.show();
                    } catch (Exception e) {

                        Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                getFragmentManager().popBackStack("RegisterToRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);



            }
        });

        return Register;
    }






    }



