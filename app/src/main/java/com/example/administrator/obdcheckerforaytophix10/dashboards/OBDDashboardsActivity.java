package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsMainPoint;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.DashboardsMainAdapter;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsFirstPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsSecondPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsThirdPageFragment;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.util.ArrayList;

public class OBDDashboardsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private DashboardsMainAdapter mAdapter;
    private ArrayList<Fragment> data;
    //三个Fragment
    private OBDDashboardsFirstPageFragment fFirst;
    private OBDDashboardsSecondPageFragment fSecond;
    private OBDDashboardsThirdPageFragment fThird;

    private ArrayList<DashboardsMainPoint> points;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_obddashboards);

        initView();
        //为data添加Fragment
        data.add(fFirst);
        data.add(fSecond);
        data.add(fThird);
        mAdapter = new DashboardsMainAdapter(getSupportFragmentManager(), data);
        vp.setAdapter(mAdapter);
        //设置监听
        vp.addOnPageChangeListener(this);


    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.dashboards_main_vp);
        data = new ArrayList<>();
        fFirst = new OBDDashboardsFirstPageFragment();
        fSecond = new OBDDashboardsSecondPageFragment();
        fThird = new OBDDashboardsThirdPageFragment();
        points = new ArrayList<>();
        ll = (LinearLayout) findViewById(R.id.dashboards_mian_bootom_ll);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        ll.removeAllViews();

        LogUtil.fussenLog().d(position);

        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScreenHeight(this);
        //1080   1776
        LogUtil.fussenLog().d("宽" + width + "高" + height + "---");

        LogUtil.fussenLog().d((int) (((float) (56.0 / (float) width)) * width) + "这个是什么鬼");

        for (int i = 0; i < data.size(); i++) {


            DashboardsMainPoint point = new DashboardsMainPoint(this);

            if (i == position) {
                point.setSelected(true);
            } else {
                point.setSelected(false);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (((float) (56.0 / (float) width)) * width),
                    (int) (((float) (56.0 / (float) height)) * height));
            params.leftMargin = 0;
            params.topMargin = (int) (((float) (44 / 647)) * height);
            ll.addView(point, params);
            ll.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
