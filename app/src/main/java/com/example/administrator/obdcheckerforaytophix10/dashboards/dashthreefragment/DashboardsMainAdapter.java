package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsMainPoint;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/8/4.
 */

public class DashboardsMainAdapter extends FragmentPagerAdapter  {

    private ArrayList<Fragment> data;

    public DashboardsMainAdapter(FragmentManager fm, ArrayList<Fragment> data) {
        super(fm);
        this.data = data;
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
    public void destroyItem(ViewGroup container, int position, Object object) {
    }


}
