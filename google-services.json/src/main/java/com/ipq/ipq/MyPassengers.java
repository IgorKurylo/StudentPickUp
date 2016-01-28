package com.ipq.ipq;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;



    public class MyPassengers extends android.support.v4.app.Fragment {
    String[] Names;
    ArrayList<String> Pass=new ArrayList<String>();
    String [] Init;
    String str1,str2,Name,Addrs,Mobile;
        View Back;
    ListView Passenger_List;
    TextView title;
    public View list;
    public ArrayList<Passenger> passengers=null;
        ArrayAdapter<String> adapter=null;
        ImageView SMS,Phone;
        public  String tel;
      public  Uri uri;

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
            Pass.clear();


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
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   tel=passengers.get(position).getPhone();
                    final Dialog dialog=new Dialog(view.getContext());
                    dialog.setTitle("אפשריות יצירת קשר");
                    dialog.setContentView(R.layout.dialog_custom);
                    SMS=(ImageView)dialog.findViewById(R.id.SMS);
                    Phone=(ImageView)dialog.findViewById(R.id.Phone);
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
                           Intent sms=new Intent(Intent.ACTION_VIEW);

                           sms.putExtra("address",tel);
                           sms.setData(Uri.parse("smsto:"+tel));

                           sms.setType("vnd.android-dir/mms-sms");

                           startActivity(sms);



                       }
                   });
                  dialog.show();

                }
            });
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
