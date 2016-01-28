package com.ipq.ipq.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ipq.ipq.DBQ;
import com.ipq.ipq.IpqMain;
import com.ipq.ipq.R;


public class UpdateData extends android.support.v4.app.Fragment{
    public View update;
    public String phone="",address="",city="",Password="";
    public String UpdateDetails="";
    EditText btnPhone,btnAddress,btnCity,Pass;
        Button btnSubmit;
    public int ID;
    ProgressBar updating;
     Handler callBack;
        public View Back;
        public TextView textView;

    public UpdateData()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callBack=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1)
                {
                    Toast.makeText(update.getContext(),"עודכן בהצלחה",Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack("UpdateData", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                }
                if(msg.what==-1)
                {
                    Toast.makeText(update.getContext(),"Updated Failed",Toast.LENGTH_LONG).show();

                    updating.setVisibility(View.GONE);
                    updating.setVisibility(View.INVISIBLE);

                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        update=inflater.inflate(R.layout.up_date_data,container,false);
        btnPhone = (EditText) update.findViewById(R.id.btnPhone);
        btnAddress=(EditText) update.findViewById(R.id.btnAddress);
        btnSubmit= (Button)update.findViewById(R.id.submit);
        btnCity= (EditText)update.findViewById(R.id.btnCity);
        Pass=(EditText)update.findViewById(R.id.btnPass);
        textView=(TextView)update.findViewById(R.id.textupdate);
        updating=(ProgressBar)update.findViewById(R.id.Updating);
        updating.setVisibility(View.GONE);
        Back=(View)update.findViewById(R.id.backbtn);
        textView.setVisibility(View.INVISIBLE);
        ID= IpqMain.getId();
        UpdateDetails=getResources().getString(R.string.UpdateDetails);
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(update.getContext());
                alert.setTitle("עדכן טלפון");
                alert.setMessage("הכנס טלפון חדש");

                // Set an EditText view to get user input
                final EditText input = new EditText(update.getContext());
                alert.setView(input);
                input.setInputType(InputType.TYPE_CLASS_PHONE);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(update.getContext(), "הטלפון החדש:"+input.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        phone = input.getText().toString();
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

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(update.getContext());
                alert.setTitle("עדכן כתובת");
                alert.setMessage("הכנס כתובת חדשה");

                // Set an EditText view to get user input
                final EditText input = new EditText(update.getContext());
                alert.setView(input);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(update.getContext(), "הכתובת המעודכנת:"+input.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        address = input.getText().toString();
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

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(update.getContext());
                alert.setTitle("עדכן עיר");
                alert.setMessage("הכנס עיר מעודכנת");

                // Set an EditText view to get user input
                final EditText input = new EditText(update.getContext());
                alert.setView(input);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(update.getContext(), "העיר המעודכנת"+input.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        city = input.getText().toString();
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

        Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(update.getContext());
                alert.setTitle("עדכון סיסמה");
                alert.setMessage("הכנס סיסמה חדשה");

                // Set an EditText view to get user input
                final EditText input = new EditText(update.getContext());
                alert.setView(input);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(update.getContext(), "הסיסמא המעודכנת :"+input.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        Password = input.getText().toString();
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(phone.equals("") && address.equals("") && city.equals("") && Password.equals("") )
                {
                    Toast.makeText(v.getContext(),"נא למלא לפחות שדה אחת לעידכון",Toast.LENGTH_LONG).show();
                }
                else
                {

                    DBQ.UpdateData(UpdateDetails, ID, address, phone, city, Password, callBack);
                    textView.setVisibility(View.VISIBLE);

                    updating.setVisibility(View.GONE);
                    updating.setVisibility(View.VISIBLE);
                }





            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                getFragmentManager().popBackStack("UpdateData", FragmentManager.POP_BACK_STACK_INCLUSIVE);



            }
        });

        return update;
    }
    }


