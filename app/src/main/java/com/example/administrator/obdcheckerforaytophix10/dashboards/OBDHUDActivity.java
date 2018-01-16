package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsHUDView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;


public class OBDHUDActivity extends AppCompatActivity implements View.OnClickListener {

    private MyOrientationEventListener mListener;
    private int orientation;
    ;
    private DashboardsHUDView view_hud;
    //上面的可点击的标题栏     整体的可点击的布局
    private RelativeLayout re_title, re_all;
    private ImageView iv_finish, iv_other;
    //接收取消动画的广播     改变字体颜色的广播
    private BroadcastReceiver br , br_color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_fragment_replace);


        initView();
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                myAnimCTwo(re_title);
            }
        };

        br_color = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int colorValue = FileLTool.getOutInstance().getQueryKey("obdHudcolor").getValue();
                if (colorValue == 1){
                    view_hud.setColorId(R.color.colorHUDtextColor);
                }else if (colorValue == 2){
                    view_hud.setColorId(R.color.color_loght_bule);
                }else if (colorValue == 3){
                    view_hud.setColorId(R.color.colorWhite);
                }else if (colorValue == 4){
                    view_hud.setColorId(R.color.color_yellow);
                }else if (colorValue == 5){
                    view_hud.setColorId(R.color.color_blue);
                }
            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("hudanimdiamiss");
        registerReceiver(br, intentFilter);

        IntentFilter filter = new IntentFilter();
        filter.addAction("changedHudColor");
        registerReceiver(br_color , filter);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setContentView(R.layout.activity_main_fragment_replace);
        initView();

    }

    private void initView() {
        orientation = getResources().getConfiguration().orientation;
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
        view_hud = (DashboardsHUDView) findViewById(R.id.main_hud);
        view_hud.setOrientation(orientation);
        re_title = (RelativeLayout) findViewById(R.id.re_obd_hud_title);
        re_all = (RelativeLayout) findViewById(R.id.re_obd_hud_parent);
        re_all.setOnClickListener(this);
        re_title.setVisibility(View.GONE);
        iv_finish = (ImageView) findViewById(R.id.iv_obdhud_finish);
        iv_other = (ImageView) findViewById(R.id.iv_obdhud_other);
        iv_finish.setOnClickListener(this);
        iv_other.setOnClickListener(this);
        int colorValue = FileLTool.getOutInstance().getQueryKey("obdHudcolor").getValue();
        if (colorValue == 1){
            view_hud.setColorId(R.color.colorHUDtextColor);
        }else if (colorValue == 2){
            view_hud.setColorId(R.color.color_loght_bule);
        }else if (colorValue == 3){
            view_hud.setColorId(R.color.colorWhite);
        }else if (colorValue == 4){
            view_hud.setColorId(R.color.color_yellow);
        }else if (colorValue == 5){
            view_hud.setColorId(R.color.color_blue);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListener.disable();
        unregisterReceiver(br);
        unregisterReceiver(br_color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_obd_hud_parent:
                re_all.setClickable(false);
                //设置标题栏出现动画
                myAnimCOne(re_title);
                re_title.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4500);
                            re_all.setClickable(true);
                            //设置标题栏消失动画的  广播
                            Intent intent = new Intent("hudanimdiamiss");
                            sendBroadcast(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.iv_obdhud_finish:
                finish();
                break;
            case R.id.iv_obdhud_other:
                Intent intent = new Intent(OBDHUDActivity.this, MainFregmentReplaceActivity.class);
                intent.putExtra("intentKey", 1);
                startActivity(intent);
                break;
        }
    }

    private void myAnimCOne(View view) {
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(view, "translationY", -ConversionUtil.myWantValue((Integer) SPUtil.get(this, "screenWidth", 0), 30), 0));
        set.setDuration(500).start();
    }

    private void myAnimCTwo(View view) {
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(view, "translationY", 0, -ConversionUtil.myWantValue((Integer) SPUtil.get(this, "screenWidth", 0), 30)));
        set.setDuration(500).start();
    }

    //继承OrientationEventListener类监听手机的旋转
    public class MyOrientationEventListener extends OrientationEventListener {

        public MyOrientationEventListener(Context context) {
            super(context);
        }

        public MyOrientationEventListener(Context context, int rate) {
            super(context, rate);
        }

        @Override
        public void onOrientationChanged(int i) {
            //i  表示偏移角度  -1的话是水平放置  0~359  手机逆时针旋转的话是 慢慢增加的
            int screenOrientation = getResources().getConfiguration().orientation;
            if (((i >= 0) && (i < 45)) || (i > 315)) {
                //设置竖屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        && i != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            } else if (i > 255 && i < 315) {
                //设置横屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            } else if (i > 45 && i < 135) {
                //设置反向横屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                }
            } else if (i > 135 && i < 225) {
                //反向竖屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                }
            }


        }
    }



}
