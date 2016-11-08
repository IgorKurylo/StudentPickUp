package com.ipq.ipq.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.R;
import com.ipq.ipq.Utils.CircleImage;

import java.util.List;

/**
 * Adapter for Passengers
 */
public class PassengerRecyleAdapter extends RecyclerView.Adapter<PassengerRecyleAdapter.ViewHolder>
{

    private Context mContext;
    private List<Passenger> passengerList;
    public OnItemClickListener itemClickListener;
    public PassengerRecyleAdapter(Context context, List<Passenger> list)
    {
        mContext=context;
        passengerList=list;

    }


    @Override
    public void onViewDetachedFromWindow(PassengerRecyleAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public PassengerRecyleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passagerlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PassengerRecyleAdapter.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(PassengerRecyleAdapter.ViewHolder holder, int position)
    {
        Passenger passenger=passengerList.get(position);
        holder.Address.setText(passenger.getAddress());
        holder.CollegeName.setText(passenger.getColleges().getNameCollege());
        holder.FullName.setText(passenger.getFullName());
        holder.Phone.setText(passenger.getPhone());

    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return passengerList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onViewRecycled(PassengerRecyleAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public LinearLayout item;
        public TextView Address;
        public TextView FullName;
        public TextView Phone;
        public TextView CollegeName;
        public CircleImage profileImage;
        public ViewHolder(View itemView)
        {
            super(itemView);
            item=(LinearLayout)itemView.findViewById(R.id.passenger_list_item);
            Address=(TextView)itemView.findViewById(R.id.direcation);
            FullName=(TextView)itemView.findViewById(R.id.passenger_name);
            CollegeName=(TextView)itemView.findViewById(R.id.College);
            Phone=(TextView)itemView.findViewById(R.id.Phone_Number);
            profileImage=(CircleImage)itemView.findViewById(R.id.profile_image);
            item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if(itemClickListener!=null)
            {
                itemClickListener.OnClickPassenger(v,getAdapterPosition());
            }

        }
    }
    public interface OnItemClickListener
    {
        void OnClickPassenger(View v,int position);
    }
}

