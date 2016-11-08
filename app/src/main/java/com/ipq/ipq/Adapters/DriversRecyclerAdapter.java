package com.ipq.ipq.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipq.ipq.Model.Driver;
import com.ipq.ipq.R;
import com.ipq.ipq.Utils.ActivityHelper;
import com.ipq.ipq.Utils.CircleImage;

import java.util.List;

/**
 * Adapter for Drivers
 */
public class DriversRecyclerAdapter extends RecyclerView.Adapter<DriversRecyclerAdapter.ViewHolder>
{

    private Context mContext;
    private List<Driver> driversList;
    public OnItemClickListener itemClickListener;
    public DriversRecyclerAdapter(Context context, List<Driver> list)
    {
        mContext=context;
        driversList=list;

    }

    public DriversRecyclerAdapter()
    {

    }

    @Override
    public void onViewDetachedFromWindow(DriversRecyclerAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public DriversRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drivers_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DriversRecyclerAdapter.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(DriversRecyclerAdapter.ViewHolder holder, int position)
    {
        Driver driver=driversList.get(position);
        holder.TimeOut.setText(driver.getTimeout());
        holder.TimeBack.setText(driver.getTimeback());
        holder.To.setText(driver.getTo().getLocation().getTitle());
        holder.From.setText(driver.getFrom().getLocation().getTitle());
        holder.StudentsNumber.setText(driver.getSit_Number());
        holder.Driver_Name.setText(ActivityHelper.ToConnectString(driver.getFirstName(),driver.getLastName()));
        holder.Date_Time.setText(driver.getDate_Time());
        holder.profileImage.setImageBitmap(null);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount()
    {
        if(driversList!=null)
            return driversList.size();
        return 0;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onViewRecycled(DriversRecyclerAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView From;
        private TextView To;
        private TextView Driver_Name;
        private TextView TimeOut,TimeBack,Date_Time;
        private TextView StudentsNumber;
        private CircleImage profileImage;
        private ImageView Navigate;
        public ViewHolder(View itemView)
        {
            super(itemView);
            To=(TextView)itemView.findViewById(R.id.To);
            From=(TextView)itemView.findViewById(R.id.From);
            Driver_Name=(TextView)itemView.findViewById(R.id.driver_Name);
            Date_Time=(TextView)itemView.findViewById(R.id.Date);
            StudentsNumber=(TextView)itemView.findViewById(R.id.studentsNumber);
            TimeOut=(TextView)itemView.findViewById(R.id.Time_Out);
            TimeBack=(TextView)itemView.findViewById(R.id.Time_back);
            profileImage=(CircleImage)itemView.findViewById(R.id.profile_image);
            Navigate=(ImageView)itemView.findViewById(R.id.show_drivers_det);
            Navigate.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if(itemClickListener!=null)
            {
                itemClickListener.OnClickDriver(v,getAdapterPosition());
            }

        }
    }
    public interface OnItemClickListener
    {
        void OnClickDriver(View v, int position);
    }
}

