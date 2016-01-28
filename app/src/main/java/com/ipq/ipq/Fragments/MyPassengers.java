package com.ipq.ipq.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipq.ipq.DataBaseSqlite;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.R;

import java.util.ArrayList;



    public class MyPassengers extends android.support.v4.app.Fragment {
    String[] Names;
    ArrayList<String> Pass=new ArrayList<String>();
    String [] Init;
    String str1,str2,Name,Addrs,Mobile;
    View Back;
    ListView Passenger_List;
    TextView title;
        public boolean Fav_Flag=false;
    public View list;
    public ArrayList<Passenger> passengers=null;
        ArrayAdapter<String> adapter=null;
        ImageView SMS,Phone;
        CheckBox Favorite;
        public  String tel;
      public  Uri uri;
     public Passenger Fav_P;
        public MyPassengers() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = inflater.inflate(R.layout.my_passengers, container, false);
        Passenger_List = (ListView) list.findViewById(R.id.listView);
        Back=(View)list.findViewById(R.id.backbtn);

        try {
            Bundle bd = getArguments();
            passengers = (ArrayList<Passenger>) bd.getSerializable("pass");
           // Pass.clear();
            for (int i = 0; i < passengers.size(); i++)
            {
                Pass.add(passengers.get(i).toString());

            }
        }catch (Exception e)
        {
            Toast.makeText(list.getContext(),"No Passengers ",Toast.LENGTH_LONG).show();
        }
         adapter=(ArrayAdapter<String>)Passenger_List.getAdapter();
        if(adapter==null)
        {
            adapter = new ArrayAdapter<String>(list.getContext(), R.layout.passagerlist, R.id.pass,Pass);
            Passenger_List.setAdapter(adapter);//create the list
            Passenger_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    tel=passengers.get(position).getPhone();
                    final Dialog dialog=new Dialog(view.getContext());
                    dialog.setContentView(R.layout.dialog_custom);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    SMS=(ImageView)dialog.findViewById(R.id.SMS);
                    Phone=(ImageView)dialog.findViewById(R.id.Phone);
                    Favorite=(CheckBox)dialog.findViewById(R.id.Fav);
                    Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent phone=new Intent(Intent.ACTION_CALL);
                           phone.setData(Uri.parse("tel:"+(tel)));
                            phone.setPackage("com.android.phone");
                            startActivity(phone);
                            dialog.dismiss();
                        }
                    });
                   SMS.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           uri=Uri.parse(tel);
                           Intent sms=new Intent(Intent.ACTION_SEND);
                           sms.putExtra(Intent.EXTRA_TEXT,tel);
                           sms.setData(Uri.parse("smsto:"+tel));
                           sms.setType("text/plain");
                          // sms.setType("vnd.android-dir/mms-sms");
                           startActivity(Intent.createChooser(sms,"בחר כיצד לשלוח הודעה"));




                       }
                   });
                    Favorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseSqlite sqllite = new DataBaseSqlite(list.getContext(), "DB_IPQ_BookMarks", null, 1);
                            Fav_P=passengers.get(position);
                            sqllite.AddBookM(Fav_P);
                            Favorite.setChecked(true);
                            Toast.makeText(v.getContext(),"נוסע הוסף לנוסעים מועדפים שלך",Toast.LENGTH_LONG).show();
                            Fav_Flag=true;
                            dialog.dismiss();

                        }
                    });
                  dialog.show();
                }
            });
            if(Fav_Flag)
            {
                Favorite.setChecked(true);
            }
        }




        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                getFragmentManager().popBackStack("MyPassengers", FragmentManager.POP_BACK_STACK_INCLUSIVE);



            }
        });
        return list;
    }
}
