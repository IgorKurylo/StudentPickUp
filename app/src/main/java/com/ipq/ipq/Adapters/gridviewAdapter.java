package com.ipq.ipq.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipq.ipq.Model.Item;
import com.ipq.ipq.R;

import java.util.ArrayList;

/***
 * Adapter for Grid view in Driver Fragment and Passenger  Fragment
 *
 */

public class gridviewAdapter extends ArrayAdapter<Integer> {

    private Context Custcontex;
    private int resouseID;
    private Integer[] Images;


    public gridviewAdapter(Context context, int resource, Integer[] images) {
        super(context, resource, images);
        this.Custcontex=context;
        resouseID=resource;
        Images=images;

    }

    @Override
    public int getCount() {
        return this.Images.length;
    }

    @Override
    public Integer getItem(int position) {
        return Images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view=convertView;
        ViewHolder holder=null;
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) Custcontex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_row, parent, false);
            holder=new ViewHolder();
            holder.imageView=(ImageView)view.findViewById(R.id.item_image);

            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }

        holder.imageView.setImageResource(Images[position]);





        return view;
    }
    class ViewHolder{

        ImageView imageView;
    }
}
