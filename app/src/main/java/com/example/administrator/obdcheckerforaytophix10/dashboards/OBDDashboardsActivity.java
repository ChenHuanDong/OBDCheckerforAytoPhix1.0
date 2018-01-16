package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.OBDL;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsMainPoint;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.MyViewPager;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.DashboardsMainAdapter;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsCustomizeFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsFirstPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsSecondPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsThirdPageFragment;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.util.ArrayList;

//变横屏那个  需要在 Aty 切换横竖屏的时候 变数据库然后  发送广播。。。。要不然 当前页面切换 然后换到其他页面会不一样，。，。，，
//今天还要把Log页面的 改好  退出时 想当于按了Stop按钮

public class OBDDashboardsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private MyViewPager vp;
    private DashboardsMainAdapter mAdapter;
    private ArrayList<Fragment> data;
    //三个Fragment
    private OBDDashboardsFirstPageFragment fFirst;
    private OBDDashboardsSecondPageFragment fSecond;
    private OBDDashboardsThirdPageFragment fThird;
    private OBDDashboardsCustomizeFragment fCustomize;

//    private DashboardsMainPoint mPointCustomize;


    private ArrayList<DashboardsMainPoint> points;
    private LinearLayout ll;

    private BroadcastReceiver br;

    //上方返回和右上角Other按钮
    private ImageView iv_retuen, iv_other;
    private int width, height;
    private int i;
    private int ii;

    //横屏相关
    private MyOrientationEventListener mListener;
    private int orientation;


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

        if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
            data.add(fCustomize);
            //mPointCustomize.setVisibility(View.VISIBLE);
        } else {
            //mPointCustomize.setVisibility(View.GONE);
        }

        mAdapter = new DashboardsMainAdapter(getSupportFragmentManager(), data);
        vp.setAdapter(mAdapter);


        //设置监听
        vp.addOnPageChangeListener(this);

        //通过广播设置是否可以滑动VP
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getBooleanExtra("scrool", true)) {
                    vp.setIsCanScroll(true);
                } else {
                    vp.setIsCanScroll(false);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("viewpagerIsScrool");
        registerReceiver(br, intentFilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orientation = getResources().getConfiguration().orientation;
        if (orientation == 2) {
            if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                for (int i = 1; i < 10; i++) {
                    DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() + 10);
                    DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i).getFloValue() - 10f);
                }
            } else {
                for (int i = 1; i < 10; i++) {
                    DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() + 10);
                    DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i + "_default").getFloValue() - 10f);
                }
            }
        }
        unregisterReceiver(br);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //在这里发送广播   三个Fragment 接收   这里边数据库
        //四种情况  竖屏-竖屏 竖屏-横屏 横屏-竖屏 横屏-横屏
        //这个 只要不等于0  就是横变竖灹竖变横
        //横屏是2  竖屏是1
        int a = getResources().getConfiguration().orientation - orientation;
        orientation = getResources().getConfiguration().orientation;

        if (a != 0) {
            if (a > 0) {
                //竖屏变横屏  要把小变小

                //这里变了要把 Top 变一下  变成   100 - 大小 - left
                //在Fragment 相对布局添加控件的时候做了left top 调换
                //把left  变一下看看  100 - NW - L ?
                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                    //自定义
                    for (int i = 1; i < 10; i++) {                      //   DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + i).getFloValue()
                        //这个先就要变回来在后面
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_" + i, 100 - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + i).getFloValue() - 5);

                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() - 10);
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i).getFloValue() + 10f);

                    }
                } else {
                    //经典
                    //dashboardsdisplaysizeandlocationwidth_1_default
                    for (int i = 1; i < 10; i++) {
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_" + i + "_default", 100 - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + i + "_default").getFloValue() - 5);

                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() - 10);
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i + "_default").getFloValue() + 10f);
                    }
                }


            } else {
                //横屏变竖屏  要把大变小
                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                    for (int i = 1; i < 10; i++) {

                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() + 10);
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i).getFloValue() - 10f);
                        //上面先就要这个在后面
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_" + i, 100 - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + i).getFloValue() - 5);
                    }
                } else {
                    for (int i = 1; i < 10; i++) {
                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() + 10);
                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i + "_default").getFloValue() - 10f);

                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_" + i + "_default", 100 - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() - DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + i + "_default").getFloValue() - 5);
                    }
                }

            }
        }

        //发送广播
        Intent intent = new Intent("Screen_Orientation_Change");
        sendBroadcast(intent);


    }

    private void initView() {
        orientation = getResources().getConfiguration().orientation;
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }


        vp = (MyViewPager) findViewById(R.id.dashboards_main_vp);
        data = new ArrayList<>();
        fFirst = new OBDDashboardsFirstPageFragment();
        fSecond = new OBDDashboardsSecondPageFragment();
        fThird = new OBDDashboardsThirdPageFragment();
        fCustomize = new OBDDashboardsCustomizeFragment();
        points = new ArrayList<>();
        ll = (LinearLayout) findViewById(R.id.dashboards_mian_bootom_ll);
        iv_retuen = (ImageView) findViewById(R.id.dashboards_main_iv_return);
        iv_other = (ImageView) findViewById(R.id.dashboards_main_iv_other);
        iv_retuen.setOnClickListener(this);
        iv_other.setOnClickListener(this);
        width = (int) SPUtil.get(this, "screenWidth", 0);
        height = (int) SPUtil.get(this, "screenHeight", 0);
        //mPointCustomize = (DashboardsMainPoint) findViewById(R.id.point_obddashboards);

    }

    //下面三个VP监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ll.removeAllViews();
        //1080   1776
        int page = 0;
        if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
            page = 4;
        } else {
            page = 3;
        }
        LogUtil.fussenLog().d("position = " + position);
        for (int i = 0; i < page; i++) {

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
                final OBDPopDialog dialog = new OBDPopDialog(this);
                View view_other = LayoutInflater.from(this).inflate(R.layout.dialog_dashboards_other, null);
                //Dashboards Mode
                ImageView iv_dash_mode = view_other.findViewById(R.id.line_dashboards_mode);
                LinearLayout ll_dash_mode = view_other.findViewById(R.id.ll_dashboards_mode);
                //Dashboards Style
                ImageView iv_dash_style = view_other.findViewById(R.id.line_dashboards_style);
                LinearLayout ll_dash_style = view_other.findViewById(R.id.ll_dashboards_style);
                //Add Display
                ImageView iv_add_display = view_other.findViewById(R.id.line_add_diaplay);
                LinearLayout ll_add_display = view_other.findViewById(R.id.ll_add_diaplay);
                //Add Dashboards
                ImageView iv_add_dashboards = view_other.findViewById(R.id.line_add_dashboards);
                LinearLayout ll_add_dashboards = view_other.findViewById(R.id.ll_add_dashboards);
                //Remove Dashboards
                ImageView iv_remove_dashboards = view_other.findViewById(R.id.line_remove_dashboards);
                LinearLayout ll_remove_dashboards = view_other.findViewById(R.id.ll_remove_dashboards);
                //load default
                ImageView iv_load_default = view_other.findViewById(R.id.line_load_defaylt);
                LinearLayout ll_load_default = view_other.findViewById(R.id.ll_load_defaylt);
                //HUD
                ImageView iv_hud = view_other.findViewById(R.id.line_hud_mode);
                LinearLayout ll_hud = view_other.findViewById(R.id.dia_dashboards_othre_hud);
                //Calibrate Devoce
                ImageView iv_calibrate_device = view_other.findViewById(R.id.line_calibrate_device);
                LinearLayout ll_calibrate_device = view_other.findViewById(R.id.ll_calibrate_device);
                // Classic  Customize  Tv
                TextView tv_cla_cus = view_other.findViewById(R.id.tv_class_customize);
                TextView tv_one_t_t = view_other.findViewById(R.id.tv_one_two_three);
                int one_tt = DBTool.getOutInstance().getQueryKey("display_style_1").getValue();
                if (one_tt == 0) {
                    tv_one_t_t.setText(getResources().getString(R.string.zero));
                } else if (one_tt == 1) {
                    tv_one_t_t.setText(getResources().getString(R.string.one));
                } else if (one_tt == 2) {
                    tv_one_t_t.setText(getResources().getString(R.string.two));
                }

                //如果是经典模式  全TM隐藏   并且把文字变了
                if (DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                    tv_cla_cus.setText(getResources().getString(R.string.classic));
                    iv_dash_mode.setVisibility(View.VISIBLE);
                    ll_dash_mode.setVisibility(View.VISIBLE);
                    iv_dash_style.setVisibility(View.VISIBLE);
                    ll_dash_style.setVisibility(View.VISIBLE);
                    iv_add_display.setVisibility(View.GONE);
                    ll_add_display.setVisibility(View.GONE);
                    iv_add_dashboards.setVisibility(View.GONE);
                    ll_add_dashboards.setVisibility(View.GONE);
                    iv_remove_dashboards.setVisibility(View.GONE);
                    ll_remove_dashboards.setVisibility(View.GONE);
                    iv_load_default.setVisibility(View.GONE);
                    ll_load_default.setVisibility(View.GONE);
                    iv_hud.setVisibility(View.GONE);
                    ll_hud.setVisibility(View.GONE);
                    iv_calibrate_device.setVisibility(View.GONE);
                    ll_calibrate_device.setVisibility(View.GONE);
                } else {
                    tv_cla_cus.setText(getResources().getString(R.string.customize));
                    iv_dash_mode.setVisibility(View.VISIBLE);
                    ll_dash_mode.setVisibility(View.VISIBLE);
                    iv_dash_style.setVisibility(View.VISIBLE);
                    ll_dash_style.setVisibility(View.VISIBLE);
                    iv_add_display.setVisibility(View.VISIBLE);
                    ll_add_display.setVisibility(View.VISIBLE);
                    iv_add_dashboards.setVisibility(View.VISIBLE);
                    ll_add_dashboards.setVisibility(View.VISIBLE);
                    iv_remove_dashboards.setVisibility(View.VISIBLE);
                    ll_remove_dashboards.setVisibility(View.VISIBLE);
                    iv_load_default.setVisibility(View.VISIBLE);
                    ll_load_default.setVisibility(View.VISIBLE);
                    iv_hud.setVisibility(View.VISIBLE);
                    ll_hud.setVisibility(View.VISIBLE);
                    iv_calibrate_device.setVisibility(View.VISIBLE);
                    ll_calibrate_device.setVisibility(View.VISIBLE);
                }
                //Dashboards Mode
                ll_dash_mode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final OBDPopDialog dia_mode = new OBDPopDialog(OBDDashboardsActivity.this);
                        View view_mode = LayoutInflater.from(OBDDashboardsActivity.this).inflate(R.layout.dialog_dashboards_mode, null);
                        LinearLayout ll_classic = view_mode.findViewById(R.id.ll_mode_classic);
                        LinearLayout ll_customize = view_mode.findViewById(R.id.ll_mode_customize);
                        final ImageView btn_class = view_mode.findViewById(R.id.iv_mode_class);
                        final ImageView btn_customize = view_mode.findViewById(R.id.iv_mode_customize);
                        Button btn_ok = view_mode.findViewById(R.id.btn_mode_ok);
                        //用来记录经典还是自定义
                        i = 0;
                        if (DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                            btn_class.setVisibility(View.VISIBLE);
                            btn_customize.setVisibility(View.GONE);
                        } else {
                            i = 1;
                            btn_class.setVisibility(View.GONE);
                            btn_customize.setVisibility(View.VISIBLE);
                        }
                        ll_classic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                i = 0;
                                btn_class.setVisibility(View.VISIBLE);
                                btn_customize.setVisibility(View.GONE);
                            }
                        });
                        ll_customize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                i = 1;
                                btn_class.setVisibility(View.GONE);
                                btn_customize.setVisibility(View.VISIBLE);
                            }
                        });
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (i == 0) {
                                    DBTool.getOutInstance().upDateIsTrueByKey("dashboardsisclassic", true);
                                } else {
                                    DBTool.getOutInstance().upDateIsTrueByKey("dashboardsisclassic", false);
                                }
                                dia_mode.dismiss();

                                Intent intent = getIntent();
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);
                            }
                        });
                        getPromptWin(dia_mode);
                        dia_mode.setContentView(view_mode);
                        dia_mode.setCanceledOnTouchOutside(true);
                        dia_mode.show();
                    }
                });

                //切换Style
                ll_dash_style.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final OBDPopDialog dia_style = new OBDPopDialog(OBDDashboardsActivity.this);
                        View view_style = LayoutInflater.from(OBDDashboardsActivity.this).inflate(R.layout.dialog_dashboards_style, null);
                        LinearLayout ll_one = view_style.findViewById(R.id.ll_style_one);
                        LinearLayout ll_two = view_style.findViewById(R.id.ll_style_two);
                        LinearLayout ll_three = view_style.findViewById(R.id.ll_style_three);
                        final ImageView iv_style_one = view_style.findViewById(R.id.iv_style_one);
                        final ImageView iv_style_two = view_style.findViewById(R.id.iv_style_two);
                        final ImageView iv_style_three = view_style.findViewById(R.id.iv_style_three);
                        Button btn_ok = view_style.findViewById(R.id.btn_style_ok);
                        ii = 0;
                        if (DBTool.getOutInstance().getQueryKey("display_style_1").getValue() == 0) {
                            iv_style_one.setVisibility(View.VISIBLE);
                            iv_style_two.setVisibility(View.GONE);
                            iv_style_three.setVisibility(View.GONE);
                        } else if (DBTool.getOutInstance().getQueryKey("display_style_1").getValue() == 1) {
                            ii = 1;
                            iv_style_one.setVisibility(View.GONE);
                            iv_style_two.setVisibility(View.VISIBLE);
                            iv_style_three.setVisibility(View.GONE);
                        } else if (DBTool.getOutInstance().getQueryKey("display_style_1").getValue() == 2) {
                            ii = 2;
                            iv_style_one.setVisibility(View.GONE);
                            iv_style_two.setVisibility(View.GONE);
                            iv_style_three.setVisibility(View.VISIBLE);
                        }
                        ll_one.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ii = 0;
                                iv_style_one.setVisibility(View.VISIBLE);
                                iv_style_two.setVisibility(View.GONE);
                                iv_style_three.setVisibility(View.GONE);
                            }
                        });
                        ll_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ii = 1;
                                iv_style_one.setVisibility(View.GONE);
                                iv_style_two.setVisibility(View.VISIBLE);
                                iv_style_three.setVisibility(View.GONE);
                            }
                        });
                        ll_three.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ii = 2;
                                iv_style_one.setVisibility(View.GONE);
                                iv_style_two.setVisibility(View.GONE);
                                iv_style_three.setVisibility(View.VISIBLE);
                            }
                        });
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ii == 0) {
                                    DBTool.getOutInstance().upDateValueByKey("display_style_1", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_2", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_3", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_4", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_5", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_6", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other7", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other8", 0);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other9", 0);
                                } else if (ii == 1) {
                                    DBTool.getOutInstance().upDateValueByKey("display_style_1", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_2", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_3", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_4", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_5", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_6", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other7", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other8", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other9", 1);
                                } else if (ii == 2) {

                                    DBTool.getOutInstance().upDateValueByKey("display_style_1", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_2", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_3", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_4", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_5", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_6", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other7", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other8", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_other9", 2);
                                }
                                Intent intent = new Intent("changeDisplay");
                                sendBroadcast(intent);
                                dia_style.dismiss();
                            }
                        });

                        getPromptWin(dia_style);
                        dia_style.setContentView(view_style);
                        dia_style.setCanceledOnTouchOutside(true);
                        dia_style.show();
                    }
                });

                //Add  Display
                ll_add_display.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LogUtil.fussenLog().d(vp.getCurrentItem() + "这个是当前页数");

                        //获取到 仪表盘数量  然后加1
                        dialog.dismiss();
                        int display_count = DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1;
                        DBTool.getOutInstance().upDateValueByKey("display_count", display_count);
                        //自定义添加仪表盘数据库
                        addMyDisplay(display_count, vp.getCurrentItem());
                        Intent intent = new Intent("changeDisplay");
                        sendBroadcast(intent);
                    }
                });

                //add dashboards
                ll_add_dashboards.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                // 切换到初始模式  Load  Default
                ll_load_default.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final OBDPopDialog dialog_load_default = new OBDPopDialog(OBDDashboardsActivity.this);
                        View view_load_defalule = LayoutInflater.from(OBDDashboardsActivity.this).inflate(R.layout.dialog_load_default, null);
                        Button btn_cancel = view_load_defalule.findViewById(R.id.btn_load_default_cancel);
                        Button btn_ok = view_load_defalule.findViewById(R.id.btn_load_default_ok);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog_load_default.dismiss();
                            }
                        });
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog_load_default.dismiss();

                                //只有竖屏能恢复初始
                                orientation = getResources().getConfiguration().orientation;
                                if (orientation == 1) {
                                    final OBDPopDialog dia_wait = new OBDPopDialog(OBDDashboardsActivity.this);
                                    View view_wait = LayoutInflater.from(OBDDashboardsActivity.this).inflate(R.layout.dialog_wait_progressbar, null);
                                    getWaitWin(dia_wait);
                                    dia_wait.setContentView(view_wait);
                                    dia_wait.setCanceledOnTouchOutside(false);
                                    dia_wait.show();

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //清空数据库调用MainActivity  重新存数据
                                            DBTool.getOutInstance().deleteAll();
                                            MainActivity.initGreedDaoData(orientation);

                                            Intent intent = new Intent("changeDisplay");
                                            sendBroadcast(intent);

                                            try {
                                                Thread.sleep(5);
                                                dia_wait.dismiss();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                } else {
                                    Toast.makeText(OBDDashboardsActivity.this, getResources().getString(R.string.pleaseturnthescreentoportrait), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        getLoadDefaultWin(dialog_load_default);
                        dialog_load_default.setContentView(view_load_defalule);
                        dialog_load_default.setCanceledOnTouchOutside(true);
                        dialog_load_default.show();
                    }
                });


                //HUD模式
                ll_hud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击带值跳转到公共替换Fragment的Aty
                        dialog.dismiss();
                        Intent intent = new Intent(OBDDashboardsActivity.this, OBDHUDActivity.class);
                        intent.putExtra("obdreplacefragment", 1);
                        startActivity(intent);
                    }
                });

                getMyWin(dialog);
                dialog.setContentView(view_other);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
        }
    }

    //自定义添加仪表盘数据库          新的仪表盘  需要有一个属性
    private void addMyDisplay(int display_count, int whatFragment) {
        OBDL obdl = new OBDL(null, "dashboardsdisplaysizeandlocationwidth_" + display_count, 40);
        DBTool.getOutInstance().insertBean(obdl);

        //这个是添加新的仪表盘要多添加的   提醒是在哪个Fragment的新增Display
        obdl.setId(null).setKey("display_in_fragment_" + display_count).setValue(whatFragment);
        DBTool.getOutInstance().insertBean(obdl);

        obdl.setId(null).setKey("display_style_" + display_count).setValue(0);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_" + display_count).setFloValue(i + 0.1f);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_" + display_count).setFloValue(i + 0.1f);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_" + display_count).setValue(0);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_" + display_count).setValue(160);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_" + display_count).setColor("00000000");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_" + display_count).setColor("ff000000");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_" + display_count).setValue(0);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_" + display_count).setValue(360);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_title_color_" + display_count).setColor("fe9002");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_title_size_" + display_count).setValue(10);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_title_position_" + display_count).setValue(35);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_value_color_" + display_count).setColor("fe9002");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_value_show_" + display_count).setIsTure(true);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_value_size_" + display_count).setValue(12);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_value_position_" + display_count).setValue(100);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_units_color_" + display_count).setColor("fe9002");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_units_size_" + display_count).setValue(7);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_units_ver_" + display_count).setValue(50);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_units_hor_" + display_count).setValue(75);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_major_width_" + display_count).setValue(10);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_major_height_" + display_count).setValue(74);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_major_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_minor_width_" + display_count).setValue(10);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_minor_height_" + display_count).setValue(80);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_minor_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_lable_show_" + display_count).setIsTure(true);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_" + display_count).setIsTure(false);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_lable_size_" + display_count).setValue(8);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_lable_offset_" + display_count).setValue(85);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_pointer_show_" + display_count).setIsTure(true);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_pointer_width_" + display_count).setValue(4);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_pointer_length_" + display_count).setValue(40);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_pointer_color_" + display_count).setColor("fe9002");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_" + display_count).setValue(5);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_center_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_range_visible_" + display_count).setIsTure(false);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_" + display_count).setValue(0);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_" + display_count).setValue(360);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_range_color_" + display_count).setColor("d63636");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_back_color_" + display_count).setColor("00a6ff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_" + display_count).setValue(60);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_title_color_" + display_count).setColor("757476");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_title_size_" + display_count).setValue(8);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_title_position_" + display_count).setValue(40);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_value_show_" + display_count).setIsTure(true);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_value_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_value_size_" + display_count).setValue(18);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_value_position_" + display_count).setValue(60);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_units_color_" + display_count).setColor("757476");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_units_size_" + display_count).setValue(8);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_units_position_" + display_count).setValue(73);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_" + display_count).setValue(2);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_range_show_" + display_count).setIsTure(true);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_two_range_color_" + display_count).setColor("00a6ff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_" + display_count).setColor("000000");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_" + display_count).setColor("000000");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_" + display_count).setValue(100);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_title_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_title_size_" + display_count).setValue(14);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_title_position_" + display_count).setValue(34);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_value_show_" + display_count).setIsTure(true);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_value_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_value_size_" + display_count).setValue(23);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_value_position_" + display_count).setValue(63);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_units_color_" + display_count).setColor("ffffffff");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_units_size_" + display_count).setValue(14);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_units_position_" + display_count).setValue(80);
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_" + display_count).setColor("000000");
        DBTool.getOutInstance().insertBean(obdl);
        obdl.setId(null).setKey("display_isRemoveDisplay_" + display_count).setIsTure(false);
        DBTool.getOutInstance().insertBean(obdl);

    }

    //下面三个都是设置Dialog出现的  win 设置
    private void getMyWin(OBDPopDialog d) {
        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(this) * 0.226666);
        lp.y = (int) (ScreenUtils.getScreenHeight(this) * 0.060278);
        win.setAttributes(lp);
    }

    private void getPromptWin(OBDPopDialog d) {
        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(this) * 0.053333);
        lp.y = (int) (ScreenUtils.getScreenHeight(this) * 0.123647);
        win.setAttributes(lp);
    }

    private void getLoadDefaultWin(OBDPopDialog d) {
        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(this) * 0.141333);
        lp.y = (int) (ScreenUtils.getScreenHeight(this) * 0.295208);
        win.setAttributes(lp);
    }

    private void getWaitWin(OBDPopDialog d) {
        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) SPUtil.get(this, "screenWidth", 0);
        lp.y = (int) SPUtil.get(this, "screenHeight", 0);
        win.setAttributes(lp);
    }


}
