package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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

public class OBDDashboardsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager vp;
    private DashboardsMainAdapter mAdapter;
    private ArrayList<Fragment> data;
    //三个Fragment
    private OBDDashboardsFirstPageFragment fFirst;
    private OBDDashboardsSecondPageFragment fSecond;
    private OBDDashboardsThirdPageFragment fThird;

    private ArrayList<DashboardsMainPoint> points;
    private LinearLayout ll;

    //上方返回和右上角Other按钮
    private ImageView iv_retuen, iv_other;


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
        iv_retuen = (ImageView) findViewById(R.id.dashboards_main_iv_return);
        iv_other = (ImageView) findViewById(R.id.dashboards_main_iv_other);
        iv_retuen.setOnClickListener(this);
        iv_other.setOnClickListener(this);
    }

    //下面三个VP监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        ll.removeAllViews();

        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScreenHeight(this);
        //1080   1776

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

    //上方两个iv点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dashboards_main_iv_return:
                finish();
                break;
            case R.id.dashboards_main_iv_other:

                break;
        }
    }
}
