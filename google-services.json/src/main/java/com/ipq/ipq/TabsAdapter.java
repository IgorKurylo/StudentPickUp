package com.ipq.ipq;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new Fragment();

        switch(position)
        {


            case 0:
                 fragment= new DriverFrag();
                return  fragment;
            case 1:
                 fragment=new PassengerFragment();

                return fragment;


        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
