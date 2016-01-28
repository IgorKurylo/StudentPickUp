package com.ipq.ipq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentSignUp extends android.support.v4.app.Fragment {

   public ImageButton TakePic;
   public  ImageView car;
    public int IMGID=0;
    public String ID,Email,Pass,Fn,city,adrss,phone;
    public EditText Adress,Phone;
    public AutoCompleteTextView City;

    public User user=null;
    final public String InsertQ="http://igortestk.netai.net/UserInsert.php";
    public int code=0;

    Handler handler;
    public View view;
    public String[] Cities={"Akko","Nahariya","Karmiel"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler=new Handler(){


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.imageviewfragment,container,false);
        ImageButton Finish=(ImageButton)view.findViewById(R.id.finish);
        Phone=(EditText)view.findViewById(R.id.Phone);
        Adress=(EditText)view.findViewById(R.id.addrss);
        City=(AutoCompleteTextView)view.findViewById(R.id.auto_complete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_dropdown_item_1line,Cities);
      City.setAdapter(adapter);
        City.setThreshold(1); //characters the user need to type..
       City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               city=City.getText().toString();
               Toast.makeText(view.getContext(),city,Toast.LENGTH_LONG).show();

           }
       });


        ID=this.getArguments().getString("ID");
        Fn=this.getArguments().getString("FullName");
        phone=this.getArguments().getString("Phone");
        Email=this.getArguments().getString("Email");
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                adrss=Adress.getText().toString();
                phone=Phone.getText().toString();
                city=City.getText().toString();
                if(adrss.equals("") && phone.equals("") && city.equals(""))
                {
                    Toast.makeText(v.getContext(),"Must Fill All Fields",Toast.LENGTH_LONG).show();
                }
                if(phone.length()<10)
                {
                    Toast.makeText(v.getContext(),"Phone Number is Not Correct",Toast.LENGTH_LONG).show();
                }
                else {
                    user = new User(ID, Fn, adrss, phone, Email, Pass, city);
                    DBQ.SignUp(InsertQ, user, handler);
                }
            }
        });
        return view;

    }

}