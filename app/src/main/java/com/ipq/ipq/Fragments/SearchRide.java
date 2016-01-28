package com.ipq.ipq.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ipq.ipq.DBQ;
import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class SearchRide extends android.support.v4.app.Fragment {

    public View Search;
    private String ShowDrivers = "";
    Button btnDate, btnSubmit, btnHome,back;
    private int mYear, mMonth, mDay;
    public String date="", hourBack, hourOut, homeOrCollage = "", city = "";
    RadioGroup radioCollagrHomeGroup;
    RadioButton CollageOrHome, collage, home;
    public String[] Cities = {"Acre", "Nahariya", "Karmiel"};
    public ArrayList<Driver> drivers = null;
    FragmentTransaction fragmentTransaction;
    private static android.support.v4.app.FragmentManager fragmentManager;
    Handler callBack;
    ProgressBar searching;


    public SearchRide() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callBack = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==-1)
                {
                    Toast.makeText(Search.getContext(),"לא פורסם טרמפ רלוונטי לפי החיפוש",Toast.LENGTH_LONG).show();
                    searching.setVisibility(View.GONE);
                    searching.setVisibility(View.INVISIBLE);

                }else
                {
                    Bundle bd = msg.getData();
                    drivers = (ArrayList<Driver>) bd.getSerializable("driver");
                    android.support.v4.app.Fragment Rl = new RideList();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("driver", (Serializable) drivers);
                    Rl.setArguments(bundle);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_list1, Rl, "R");
                    fragmentTransaction.addToBackStack("SearchRide");
                    fragmentTransaction.commit();
                }


            }


        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        Search = inflater.inflate(R.layout.search_ride, container, false);
        searching = (ProgressBar) Search.findViewById(R.id.finddriver);
        btnDate = (Button) Search.findViewById(R.id.btnDate);
        btnSubmit = (Button) Search.findViewById(R.id.submit);
        btnHome = (Button) Search.findViewById(R.id.btnHome);
        back=(Button)Search.findViewById(R.id.back);
        fragmentManager = getFragmentManager();
        searching.setVisibility(View.GONE);
        ShowDrivers=getResources().getString(R.string.ShowD);
        btnDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(Search.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Toast.makeText(Search.getContext(), date, Toast.LENGTH_LONG).show();

                            }
                        }, mYear, mMonth, mDay);

                dpd.show();
            }
        });

        collage = (RadioButton) Search.findViewById(R.id.collage);
        home = (RadioButton) Search.findViewById(R.id.home);
        radioCollagrHomeGroup = (RadioGroup) Search.findViewById(R.id.rdGrpCollageHome);
        radioCollagrHomeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                CollageOrHome = (RadioButton) Search.findViewById(checkedId);
                if (checkedId == R.id.collage) {
                    homeOrCollage = "Collage";
                    btnHome.setVisibility(View.INVISIBLE);
                    Toast.makeText(Search.getContext(), homeOrCollage,
                            Toast.LENGTH_SHORT).show();

                }
                if (checkedId == R.id.home) {
                    homeOrCollage = "home";
                    btnHome.setVisibility(View.VISIBLE);
                    Toast.makeText(Search.getContext(), homeOrCollage,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(Search.getContext());
                alert.setTitle("הגדר יעד");
                alert.setMessage("רשום את יעד הנסיעה");

                // Set an EditText view to get user input
                final AutoCompleteTextView input = new AutoCompleteTextView(Search.getContext());
                alert.setView(input);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search.getContext(),
                        android.R.layout.simple_dropdown_item_1line, Cities);
                input.setAdapter(adapter);
                input.setThreshold(1); //characters the user need to type..
                input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        city = input.getText().toString();
                        Toast.makeText(view.getContext(), city, Toast.LENGTH_LONG).show();

                    }
                });

                alert.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(Search.getContext(), input.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        city = input.getText().toString();
                        // Do something with value!
                    }
                });

                alert.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (city.equals("") && date.equals("") && homeOrCollage.equals(""))
                {
                    Toast.makeText(v.getContext(), "נא למלא את כל הפרטים לחיפוש טרמפ", Toast.LENGTH_LONG).show();
                }


                else
                {
                    try {
                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setTitle("חיפוש טרמפ");
                        alert.setMessage("לחפש לך טרמפ?");

                        alert.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                                DBQ.ShowDrivers(ShowDrivers, date, homeOrCollage, city, callBack);
                                searching.setVisibility(View.GONE);
                                searching.setVisibility(View.VISIBLE);


                            }
                        });

                        alert.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getFragmentManager().popBackStack("SearchRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            }
                        });

                        alert.show();

                    } catch (Exception e)
                    {
                          Log.e("Error", e.toString());

                    }


                }
            }
        });
          back.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {

                  getFragmentManager().popBackStack("SearchRide", FragmentManager.POP_BACK_STACK_INCLUSIVE);

              }
          });
        return Search;


    }
}
