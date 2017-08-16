package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.MainFragmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsMainPoint;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.DashboardsMainAdapter;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsFirstPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsHUDFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsSecondPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsThirdPageFragment;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.io.File;
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
    private int width, height;

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
        width = (int) SPUtil.get(this, "screenWidth", 0);
        height = (int) SPUtil.get(this, "screenHeight", 0);
    }

    //下面三个VP监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        ll.removeAllViews();


        //1080   1776

        for (int i = 0; i < data.size(); i++) {

            DashboardsMainPoint point = new DashboardsMainPoint(this);

            if (i == position) {
                point.setSelected(true);
            } else {
                point.setSelected(false);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ConversionUtil.myWantValue(width, (float) 19.44),
                    (int) ConversionUtil.myWantValue(width, (float) 19.44));
            params.leftMargin = 0;
            params.topMargin = (int) (((float) (30 / 647)) * height);
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
                OBDPopDialog dialog = new OBDPopDialog(this);
                View view_other = LayoutInflater.from(this).inflate(R.layout.dialog_dashboards_other , null);



                //HUD模式
                LinearLayout ll_hud = view_other.findViewById(R.id.dia_dashboards_othre_hud);
                ll_hud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击带值跳转到公共替换Fragment的Aty
                        Intent intent = new Intent(OBDDashboardsActivity.this , MainFragmentReplaceActivity.class);
                        intent.putExtra("obdreplacefragment" , 1);
                        startActivity(intent);
                    }
                });



                Window win = dialog.getWindow();
                WindowManager.LayoutParams lp = win.getAttributes();
                win.setGravity(Gravity.LEFT | Gravity.TOP);
                lp.x = (int) (ScreenUtils.getScreenWidth(this) * 0.226666);
                lp.y = (int) (ScreenUtils.getScreenHeight(this) * 0.060278);
                win.setAttributes(lp);
                dialog.setContentView(view_other);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
        }
    }



}
