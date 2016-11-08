package com.ipq.ipq.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ipq.ipq.Adapters.DriversRecyclerAdapter;
import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.Network.DriverNetworkManager;
import com.ipq.ipq.R;

import java.util.List;

/**
 * Show Drivers for the passengers
 */

public class Drivers_Activity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private DriversRecyclerAdapter driversRecycleAdapter;
    private DriversRecyclerAdapter.OnItemClickListener onItemClickListener ;
    private DriverNetworkManager.INetworkResult iNetworkResult;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.drivers_layout);

        toolbar=(Toolbar)findViewById(R.id.toolbar);

        InitToolbar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.RV);

        InitRecyclerView(recyclerView);

        DriverNetworkManager driverNetworkManager=new DriverNetworkManager("");
        driverNetworkManager.execute();
        iNetworkResult=new DriverNetworkManager.INetworkResult()
        {
            @Override
            public void Result(List<Driver> result)
            {

                driversRecycleAdapter =new DriversRecyclerAdapter(getApplicationContext(),result);
                driversRecycleAdapter.setItemClickListener(onItemClickListener);
                recyclerView.setAdapter(driversRecycleAdapter);

            }
        };
        onItemClickListener=new DriversRecyclerAdapter.OnItemClickListener()
        {

            @Override
            public void OnClickDriver(View v, int position)
            {

                Toast.makeText(Drivers_Activity.this, "Position"+position, Toast.LENGTH_SHORT).show();

            }
        };
        driverNetworkManager.setInetworkResult(iNetworkResult);




    }
    private void InitToolbar(Toolbar toolbar)
    {
        LinearLayout toolbarLayout=(LinearLayout)toolbar.findViewById(R.id.toolbar_layout);

        TextView MainTitle=(TextView)toolbarLayout.findViewById(R.id.main_title);

        TextView SubTitle=(TextView)toolbarLayout.findViewById(R.id.sub_title);

        MainTitle.setText(getString(R.string.driverMain));

        SubTitle.setText(getString(R.string.PassengerMode));
    }
    private void InitRecyclerView(RecyclerView recyclerView)
    {
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new DriversRecyclerAdapter());




    }

}
