package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/10/9.
 */

public class AdapterLogsDetailTabLayout extends FragmentPagerAdapter {

    private ArrayList<Fragment> data;
    private String[] title ;


    public AdapterLogsDetailTabLayout(FragmentManager fm, ArrayList<Fragment> data, String[] title) {
        super(fm);
        this.data = data;
        this.title = title;
    }



    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }



}
