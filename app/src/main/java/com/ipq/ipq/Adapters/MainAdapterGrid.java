package com.ipq.ipq.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipq.ipq.Model.Item;
import com.ipq.ipq.R;

import java.util.ArrayList;
import java.util.List;

/***
 * Adapter for Grid view in Driver Fragment and Passenger  Fragment
 *
 */

public class MainAdapterGrid extends ArrayAdapter<Item> {

    private Context Custcontex;
    private int resouseID;
    private ArrayList<Item> items=new ArrayList<Item>();


    public MainAdapterGrid(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.Custcontex=context;
        resouseID=resource;
        items=objects;

    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
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
            holder.gridTitle=(TextView)view.findViewById(R.id.grid_text);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        Item item=this.items.get(position);
        holder.imageView.setImageResource(item.getIcon());
        holder.gridTitle.setText(item.getTitle());





        return view;
    }
    class ViewHolder{
        TextView gridTitle;
        ImageView imageView;
    }
}
