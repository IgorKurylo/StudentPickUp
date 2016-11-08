package com.ipq.ipq;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.ipq.ipq.Utils.Session;


public class MainActivity extends AppCompatActivity {

    Button login,Signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.logbtn);

        final Session session=new Session(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               try {

                   Intent Login = new Intent(getApplicationContext(), LoginWindow.class);
                   startActivity(Login);
                   overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


               }catch (Exception e)
               {
                   Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_LONG).show();
               }


            }

        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent signup = new Intent(v.getContext(), SignUp.class);
                    startActivity(signup);
                }catch (Exception e)
                {
                    Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Logout)
         {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
