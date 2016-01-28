package com.ipq.ipq;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ipq.ipq.Fragments.DriverFrag;
import com.ipq.ipq.Fragments.Main_Menu;
import com.ipq.ipq.Fragments.PassengerFragment;
import com.ipq.ipq.Fragments.RideList;


public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;

        switch(position)
        {



            case 0:
                 fragment= new DriverFrag();
                return  fragment;
            case 1:
                fragment=new Main_Menu();
                return fragment;

            case 2:
                fragment=new PassengerFragment();

                return fragment;



        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position)
        {



            case 0:
                return  "נהגים";
            case 1:

            return "תפריט ראשי";
            case 2:
                return "נוסעים";




        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
