package com.ipq.ipq;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ipq.ipq.Model.User;

import java.net.URLDecoder;

public class SignUp extends ActionBarActivity {


    EditText ID, Email, Pass, FirstName, LastName,Adress,Phone;
    public AutoCompleteTextView City;
    public ProgressBar signupbar;
    String  emailuser = "", passuser = "", IDuser = "", FullName = "",Phonenum,address="",cityuser="",SignUpUrl="";
    Button Submit;
    ImageView SignUpLogo;
    public User user=null;
    public Handler handler;
    public String[] Cities=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ID = (EditText) findViewById(R.id.ID);
        Email = (EditText) findViewById(R.id.Email);
        Pass = (EditText) findViewById(R.id.Pass);
        FirstName = (EditText) findViewById(R.id.Fn);
        LastName = (EditText) findViewById(R.id.Ln);
        Submit = (Button) findViewById(R.id.submit);
        Phone=(EditText)findViewById(R.id.Phone);
        Adress=(EditText)findViewById(R.id.addrss);
        Cities=getResources().getStringArray(R.array.cities_arr);
        City=(AutoCompleteTextView)findViewById(R.id.auto_complete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.cosrum_text_view,Cities);
        City.setAdapter(adapter);
        City.setThreshold(1); //characters the user need to type..
        signupbar=(ProgressBar)findViewById(R.id.signupsaving);
        signupbar.setVisibility(View.GONE);
        SignUpUrl=getResources().getString(R.string.SignUp);
        getSupportActionBar().setTitle("הרשמה");
        handler=new Handler(){


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1)
                {
                    Toast.makeText(getApplicationContext(),"הרשמה עברה בהצלחה",Toast.LENGTH_LONG).show();
                    Intent main=new Intent(getApplicationContext(),LoginWindow.class);
                    startActivity(main);

                }
                if(msg.what==-1)
                {
                    Toast.makeText(getApplicationContext(),"הרשמה לא עברה בהצלחה נסה שנית",Toast.LENGTH_LONG).show();
                    signupbar.setVisibility(View.GONE);
                    signupbar.setVisibility(View.INVISIBLE);
                    Submit.setClickable(true);
                    user=null;

                }
            }
        };
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FullName="";
                    IDuser = ID.getText().toString();
                    FullName+=" "+ URLDecoder.decode(FirstName.getText().toString());
                    FullName+=" "+URLDecoder.decode(LastName.getText().toString());
                    emailuser = Email.getText().toString();
                    passuser = Pass.getText().toString();
                    Phonenum=Phone.getText().toString();
                    address=Adress.getText().toString();
                    City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            cityuser=City.getText().toString();
                            Toast.makeText(view.getContext(),cityuser,Toast.LENGTH_LONG).show();

                        }
                    });
                    cityuser=City.getText().toString();
                    if(IDuser.equals("") )
                    {
                        ID.setError("יש למלא מספר תעודת זהות");
                    }
                    else if(FullName.equals(""))
                    {
                        FirstName.setError("יש למלא שם פרטי");
                        LastName.setError("יש למלא שם משפחה");
                    }
                    else if(emailuser.equals(""))
                    {
                       Email.setError("נא למלא אימייל");
                    }
                    else if( passuser.equals(""))
                    {
                        Pass.setError("נא למלא סיסמה");
                    }
                    else if(!emailuser.contains("@"))
                    {
                        Email.setError("אימייל לא תקין");
                    }
                    else if(address.equals("") )
                    {
                       Adress.setError("נא למלא כתובת מלאה");
                    }
                    else if(Phonenum.length()<10)
                    {
                        Phone.setError("מספר טלפון לא תקין");
                    }else  if(cityuser.equals(""))
                    {
                        City.setError("נא רשום עיר מגורים");
                    }else{


                        user = new User(IDuser, FullName, address, Phonenum, emailuser, passuser, cityuser);
                        signupbar.setVisibility(View.GONE);
                        signupbar.setVisibility(View.VISIBLE);
                        Submit.setClickable(false);
                        DBQ.SignUp(SignUpUrl, user, handler);
                    }

                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:

                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent back=new Intent(getApplicationContext(),MainActivity.class);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        startActivity(back);
        finish();
    }

}









