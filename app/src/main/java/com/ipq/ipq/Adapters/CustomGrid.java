package com.ipq.ipq.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

/***
 * Adapter for Grid view in Driver Fragment and Passenger  Fragment
 *
 */

public class CustomGrid extends BaseAdapter {

    private Context Custcontex;
    private final int[] Imageid;


    public CustomGrid(int[] imageid, Context custcontex) {
        Imageid = imageid;
        Custcontex = custcontex;
    }

    @Override
    public int getCount() {
        return this.Imageid.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imgview;
       // View view;
//        LayoutInflater inflater=(LayoutInflater)Custcontex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            imgview=new ImageView(Custcontex);
            imgview.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imgview.setPadding(8,8,8,8);
            imgview.setBackgroundResource(this.Imageid[position]);
            imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
        else{
            imgview=(ImageView)convertView;
        }
        return imgview;
    }
}
