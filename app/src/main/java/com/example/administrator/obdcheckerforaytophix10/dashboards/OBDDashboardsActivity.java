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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
                int one_tt = (int) SPUtil.get(this, "display_style_1", 0);
                if (one_tt == 0) {
                    tv_one_t_t.setText(getResources().getString(R.string.zero));
                } else if (one_tt == 1) {
                    tv_one_t_t.setText(getResources().getString(R.string.one));
                } else if (one_tt == 2) {
                    tv_one_t_t.setText(getResources().getString(R.string.two));
                }

                //如果是经典模式  全TM隐藏   并且把文字变了
                if ((boolean) SPUtil.get(OBDDashboardsActivity.this, "dashboardsisclassic", true)) {
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
                        if ((boolean) SPUtil.get(OBDDashboardsActivity.this, "dashboardsisclassic", true)) {
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
                                if (1 == 0){
                                    SPUtil.put(OBDDashboardsActivity.this, "dashboardsisclassic", true);
                                }else {
                                    SPUtil.put(OBDDashboardsActivity.this, "dashboardsisclassic", false);
                                }
                                dia_mode.dismiss();
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
                        if ((int) SPUtil.get(OBDDashboardsActivity.this, "display_style_1", 0) == 0) {
                            iv_style_one.setVisibility(View.VISIBLE);
                            iv_style_two.setVisibility(View.GONE);
                            iv_style_three.setVisibility(View.GONE);
                        } else if ((int) SPUtil.get(OBDDashboardsActivity.this, "display_style_1", 0) == 1) {
                            ii = 1;
                            iv_style_one.setVisibility(View.GONE);
                            iv_style_two.setVisibility(View.VISIBLE);
                            iv_style_three.setVisibility(View.GONE);
                        } else if ((int) SPUtil.get(OBDDashboardsActivity.this, "display_style_1", 0) == 2) {
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
                                ii = 2 ;
                                iv_style_one.setVisibility(View.GONE);
                                iv_style_two.setVisibility(View.GONE);
                                iv_style_three.setVisibility(View.VISIBLE);
                            }
                        });
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ii == 0){
                                    SPUtil.put(OBDDashboardsActivity.this, "display_style_1", 0);
                                }else if (ii == 1){
                                    SPUtil.put(OBDDashboardsActivity.this, "display_style_1", 1);
                                }else if (ii == 2){
                                    SPUtil.put(OBDDashboardsActivity.this, "display_style_1", 2);
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

}
