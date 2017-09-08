package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.MainFragmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.OBDL;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsMainPoint;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.MyViewPager;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.DashboardsMainAdapter;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsCustomizeFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsFirstPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsHUDFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsSecondPageFragment;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsThirdPageFragment;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OBDDashboardsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private MyViewPager vp;
    private DashboardsMainAdapter mAdapter;
    private ArrayList<Fragment> data;
    //三个Fragment
    private OBDDashboardsFirstPageFragment fFirst;
    private OBDDashboardsSecondPageFragment fSecond;
    private OBDDashboardsThirdPageFragment fThird;
    private OBDDashboardsCustomizeFragment fCustomize;

    private DashboardsMainPoint mPointCustomize;


    private ArrayList<DashboardsMainPoint> points;
    private LinearLayout ll;

    private BroadcastReceiver br;

    //上方返回和右上角Other按钮
    private ImageView iv_retuen, iv_other;
    private int width, height;
    private int i;
    private int ii;

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

        if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()){
            data.add(fCustomize);
            mPointCustomize.setVisibility(View.VISIBLE);
        }else {
            mPointCustomize.setVisibility(View.GONE);
        }

        mAdapter = new DashboardsMainAdapter(getSupportFragmentManager(), data);
        vp.setAdapter(mAdapter);


        //设置监听
        vp.addOnPageChangeListener(this);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                   if (intent.getBooleanExtra("scrool" , true)){
                        vp.setIsCanScroll(true);
                   }else {
                       vp.setIsCanScroll(false);
                   }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("viewpagerIsScrool");
        registerReceiver(br , intentFilter);


    }

    private void initView() {
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
        mPointCustomize = (DashboardsMainPoint) findViewById(R.id.point_obddashboards);
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
                                } else if (ii == 1) {
                                    DBTool.getOutInstance().upDateValueByKey("display_style_1", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_2", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_3", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_4", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_5", 1);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_6", 1);
                                } else if (ii == 2) {

                                    DBTool.getOutInstance().upDateValueByKey("display_style_1", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_2", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_3", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_4", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_5", 2);
                                    DBTool.getOutInstance().upDateValueByKey("display_style_6", 2);
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

                        LogUtil.fussenLog().d(vp.getCurrentItem()+"这个是当前页数");

                        //获取到 仪表盘数量  然后加1
                        dialog.dismiss();
                        int display_count = DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1;
                        DBTool.getOutInstance().upDateValueByKey("display_count", display_count);
                        //自定义添加仪表盘数据库
                        addMyDisplay(display_count , vp.getCurrentItem() );
                        Intent intent = new Intent("changeDisplay");
                        sendBroadcast(intent);
                    }
                });

                //add dashboards
                ll_add_dashboards.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //LogUtil.fussenLog().d(getVisibleFragment().getId() + "------");
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
                                //自定义更改方法
                                //load_default();
                                //清空数据库调用MainActivity  重新存数据
                                DBTool.getOutInstance().deleteAll();
                                MainActivity.initGreedDaoData();

                                Intent intent = new Intent("changeDisplay");
                                sendBroadcast(intent);
                                dialog_load_default.dismiss();
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
                        Intent intent = new Intent(OBDDashboardsActivity.this, MainFragmentReplaceActivity.class);
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
    private void addMyDisplay(int display_count , int whatFragment) {
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


    private void load_default() {
        DBTool.getOutInstance().upDateValueByKey("display_style_1", 0);
        //一共仪表盘的数量
        DBTool.getOutInstance().upDateValueByKey("display_count", 9);

        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_1", 40);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_1", 6.667f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_1", 1.748f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_2", 40);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_2", 53.333f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_2", 1.748f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_3", 40);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_3", 6.667f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_3", 34.266f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_4", 40);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_4", 53.333f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_4", 34.266f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_5", 40);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_5", 6.667f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_5", 66.783f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_6", 40);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_6", 53.333f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_6", 66.783f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_7", 59);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_7", 20.5333f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_7", 3.1469f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_8", 59);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_8", 20.5333f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_8", 51.7483f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_9", 80);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_9", 9.8667f);
        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_9", 15.3846f);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_1", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_2", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_3", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_4", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_5", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_6", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_1", 160);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_2", 160);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_3", 160);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_4", 160);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_5", 160);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_6", 160);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_innercolor_1", "00000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_innercolor_2", "00000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_innercolor_3", "00000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_innercolor_4", "00000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_innercolor_5", "00000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_innercolor_6", "00000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_outercolor_1", "ff000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_outercolor_2", "ff000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_outercolor_3", "ff000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_outercolor_4", "ff000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_outercolor_5", "ff000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_style_back_outercolor_6", "ff000000");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_start_1", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_start_2", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_start_3", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_start_4", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_start_5", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_start_6", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_end_1", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_end_2", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_end_3", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_end_4", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_end_5", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplayconfiguration_end_6", 360);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_title_color_1", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_title_color_2", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_title_color_3", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_title_color_4", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_title_color_5", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_title_color_6", "fe9002");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_size_1", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_size_2", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_size_3", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_size_4", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_size_5", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_size_6", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_position_1", 35);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_position_2", 35);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_position_3", 35);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_position_4", 35);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_position_5", 35);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_title_position_6", 35);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_value_color_1", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_value_color_2", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_value_color_3", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_value_color_4", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_value_color_5", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_value_color_6", "fe9002");
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_value_show_1", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_value_show_2", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_value_show_3", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_value_show_4", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_value_show_5", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_value_show_6", true);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_size_1", 12);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_size_2", 12);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_size_3", 12);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_size_4", 12);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_size_5", 12);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_size_6", 12);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_position_1", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_position_2", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_position_3", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_position_4", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_position_5", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_value_position_6", 100);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_units_color_1", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_units_color_2", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_units_color_3", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_units_color_4", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_units_color_5", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_units_color_6", "fe9002");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_size_1", 7);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_size_2", 7);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_size_3", 7);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_size_4", 7);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_size_5", 7);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_size_6", 7);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_ver_1", 50);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_ver_2", 50);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_ver_3", 50);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_ver_4", 50);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_ver_5", 50);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_ver_6", 50);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_hor_1", 75);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_hor_2", 75);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_hor_3", 75);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_hor_4", 75);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_hor_5", 75);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_units_hor_6", 75);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_width_1", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_width_2", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_width_3", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_width_4", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_width_5", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_width_6", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_height_1", 74);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_height_2", 74);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_height_3", 74);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_height_4", 74);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_height_5", 74);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_major_height_6", 74);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_major_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_major_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_major_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_major_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_major_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_major_color_6", "ffffffff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_width_1", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_width_2", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_width_3", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_width_4", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_width_5", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_width_6", 10);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_height_1", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_height_2", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_height_3", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_height_4", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_height_5", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_minor_height_6", 80);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_minor_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_minor_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_minor_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_minor_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_minor_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_minor_color_6", "ffffffff");
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_show_1", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_show_2", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_show_3", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_show_4", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_show_5", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_show_6", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_rotate_1", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_rotate_2", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_rotate_3", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_rotate_4", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_rotate_5", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_lable_rotate_6", false);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_size_1", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_size_2", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_size_3", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_size_4", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_size_5", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_size_6", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_offset_1", 85);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_offset_2", 85);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_offset_3", 85);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_offset_4", 85);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_offset_5", 85);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_lable_offset_6", 85);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_pointer_show_1", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_pointer_show_2", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_pointer_show_3", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_pointer_show_4", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_pointer_show_5", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_pointer_show_6", true);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_width_1", 4);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_width_2", 4);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_width_3", 4);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_width_4", 4);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_width_5", 4);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_width_6", 4);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_length_1", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_length_2", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_length_3", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_length_4", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_length_5", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_length_6", 40);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_pointer_color_1", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_pointer_color_2", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_pointer_color_3", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_pointer_color_4", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_pointer_color_5", "fe9002");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_pointer_color_6", "fe9002");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_rad_1", 5);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_rad_2", 5);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_rad_3", 5);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_rad_4", 5);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_rad_5", 5);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_pointer_rad_6", 5);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_center_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_center_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_center_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_center_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_center_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_center_color_6", "ffffffff");
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_range_visible_1", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_range_visible_2", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_range_visible_3", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_range_visible_4", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_range_visible_5", false);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_range_visible_6", false);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_startAngle_1", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_startAngle_2", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_startAngle_3", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_startAngle_4", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_startAngle_5", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_startAngle_6", 0);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_endAngle_1", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_endAngle_2", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_endAngle_3", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_endAngle_4", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_endAngle_5", 360);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_range_endAngle_6", 360);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_range_color_1", "d63636");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_range_color_2", "d63636");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_range_color_3", "d63636");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_range_color_4", "d63636");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_range_color_5", "d63636");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_range_color_6", "d63636");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_1", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_2", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_3", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_4", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_5", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_6", "00a6ff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_1", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_2", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_3", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_4", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_5", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_6", 60);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_1", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_2", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_3", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_4", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_5", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_6", "757476");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_1", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_2", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_3", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_4", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_5", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_6", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_1", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_2", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_3", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_4", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_5", 40);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_6", 40);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_value_show_1", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_value_show_2", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_value_show_3", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_value_show_4", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_value_show_5", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_value_show_6", true);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_6", "ffffffff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_1", 18);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_2", 18);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_3", 18);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_4", 18);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_5", 18);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_6", 18);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_1", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_2", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_3", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_4", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_5", 60);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_6", 60);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_1", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_2", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_3", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_4", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_5", "757476");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_6", "757476");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_1", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_2", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_3", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_4", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_5", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_6", 8);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_1", 73);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_2", 73);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_3", 73);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_4", 73);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_5", 73);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_6", 73);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_6", "ffffffff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_1", 2);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_2", 2);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_3", 2);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_4", 2);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_5", 2);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_6", 2);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_range_show_1", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_range_show_2", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_range_show_3", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_range_show_4", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_range_show_5", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_two_range_show_6", true);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_1", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_2", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_3", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_4", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_5", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_6", "00a6ff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_1", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_2", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_3", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_4", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_5", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_6", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_1", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_2", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_3", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_4", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_5", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_6", "000000");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_1", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_2", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_3", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_4", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_5", 100);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_6", 100);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_6", "ffffffff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_1", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_2", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_3", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_4", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_5", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_6", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_1", 34);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_2", 34);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_3", 34);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_4", 34);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_5", 34);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_6", 34);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_three_value_show_1", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_three_value_show_2", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_three_value_show_3", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_three_value_show_4", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_three_value_show_5", true);
        DBTool.getOutInstance().upDateIsTrueByKey("dashboardsdisplay_three_value_show_6", true);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_6", "ffffffff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_1", 23);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_2", 23);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_3", 23);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_4", 23);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_5", 23);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_6", 23);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_1", 63);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_2", 63);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_3", 63);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_4", 63);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_5", 63);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_6", 63);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_1", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_2", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_3", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_4", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_5", "ffffffff");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_6", "ffffffff");
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_1", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_2", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_3", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_4", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_5", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_6", 14);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_1", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_2", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_3", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_4", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_5", 80);
        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_6", 80);
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_1", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_2", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_3", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_4", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_5", "000000");
        DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_6", "000000");
        DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_1", false);
        DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_2", false);
        DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_3", false);
        DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_4", false);
        DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_5", false);
        DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_6", false);

    }




}
