package com.example.administrator.obdcheckerforaytophix10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.example.administrator.obdcheckerforaytophix10.main.MainOBDFragment;
import com.example.administrator.obdcheckerforaytophix10.main.MainPersionalFragment;
import com.example.administrator.obdcheckerforaytophix10.main.MainSpecialFragment;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //底部三个RadioButton
    private RadioButton mRadioButton_obd, mRadioButton_special, mRadioButton_persional;
    //三个页面的Fragment
    private MainOBDFragment mOBDFragment;
    private MainSpecialFragment mSpecialFragment;
    private MainPersionalFragment mPersionalFragment;
    //判断现在是在哪一页
    private int page = 1;
    private Fragment current_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //初始化
        initView();
        mRadioButton_obd.performClick();
        //设置第一个按钮不可点击
        mRadioButton_obd.setClickable(false);

        //自定义初始化数据库
        initGreedDaoData();
        //自定义初始化第一个表
        if (FileLTool.getOutInstance().isSave("isFirst")) {
        } else {
            initFileGreenDao();
        }


//        List<OBDL> list = DBTool.getOutInstance().queryAll();
//        for (OBDL o : list) {
//            LogUtil.fussenLog().d("\n" + o.getId() + "\n" +
//                    o.getKey() + "\n" + o.getValue() + "\n" + o.getColor() + "\n" +
//                    o.getIsTure() + "\n" + o.getFloValue());
//        }

    }

    private void initFileGreenDao() {
        FileL fileL = new FileL();
        fileL.setId(null).setKey("isFirst").setTure(true);
        FileLTool.getOutInstance().insertBean(fileL);
        fileL.setId(null).setKey("obdHudcolor").setValue(1);
        FileLTool.getOutInstance().insertBean(fileL);

    }


    public static void initGreedDaoData() {
        if (DBTool.getOutInstance().isSave("isFirst")) {
            //LogUtil.fussenLog().d("不为空");
        } else {
            // LogUtil.fussenLog().d("空的");
            //第一次判断为空的时候把所有初始化全存进去   这个初始化是为了下次不再把数据二次存在数据库数据库
            //这里还需要把默认的除了第一页的六个仪表盘之外的  789  三个给存到数据库里面
            OBDL obdl = new OBDL(null, "isFirst", true);
            DBTool.getOutInstance().insertBean(obdl);
            //保存一共仪表盘的个数
            obdl.setId(null).setKey("display_count").setValue(9);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsisclassic").setTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_style_1").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_style_2").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_style_3").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_style_4").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_style_5").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_style_6").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);

            //变成经典的还要存一遍    大小位置九个单独存   其他九个只存一遍
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_1_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_1_default").setFloValue(6.667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_1_default").setFloValue(1.748f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_2_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_2_default").setFloValue(53.333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_2_default").setFloValue(1.748f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_3_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_3_default").setFloValue(6.667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_3_default").setFloValue(34.266f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_4_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_4_default").setFloValue(53.333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_4_default").setFloValue(34.266f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_5_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_5_default").setFloValue(6.667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_5_default").setFloValue(66.783f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_6_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_6_default").setFloValue(53.333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_6_default").setFloValue(66.783f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_7_default").setValue(59);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_7_default").setFloValue(20.5333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_7_default").setFloValue(3.1469f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_8_default").setValue(59);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_8_default").setFloValue(20.5333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_8_default").setFloValue(51.7483f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_9_default").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_9_default").setFloValue(9.8667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_9_default").setFloValue(15.3846f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_1_default").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_1_default").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_1_default").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_1_default").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_1_default").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_1_default").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_title_color_1_default").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_title_size_1_default").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_title_position_1_default").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_value_color_1_default").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_value_show_1_default").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_value_size_1_default").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_value_position_1_default").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_units_color_1_default").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_units_size_1_default").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_units_ver_1_default").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_units_hor_1_default").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_major_width_1_default").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_major_height_1_default").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_major_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_minor_width_1_default").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_minor_height_1_default").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_minor_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_lable_show_1_default").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_1_default").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_lable_size_1_default").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_1_default").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_1_default").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_1_default").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_1_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_1_default").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_1_default").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_center_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_range_visible_1_default").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_1_default").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_1_default").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_range_color_1_default").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_1_default").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_1_default").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_1_default").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_1_default").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_1_default").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_1_default").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_1_default").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_1_default").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_1_default").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_1_default").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_1_default").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_1_default").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_1_default").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_1_default").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_1_default").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_1_default").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_1_default").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_1_default").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_1_default").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_1_default").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_1_default").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_1_default").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_1_default").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_1_default").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_1_default").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_1_default").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);

            obdl.setId(null).setKey("display_isRemoveDisplay_1_default").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);


//--------------------------------------------------------------------------------------------------------------
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_1").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_1").setFloValue(6.667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_1").setFloValue(1.748f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_2").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_2").setFloValue(53.333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_2").setFloValue(1.748f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_3").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_3").setFloValue(6.667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_3").setFloValue(34.266f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_4").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_4").setFloValue(53.333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_4").setFloValue(34.266f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_5").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_5").setFloValue(6.667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_5").setFloValue(66.783f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_6").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_6").setFloValue(53.333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_6").setFloValue(66.783f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_7").setValue(59);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_7").setFloValue(20.5333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_7").setFloValue(3.1469f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_8").setValue(59);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_8").setFloValue(20.5333f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_8").setFloValue(51.7483f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocationwidth_9").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_left_9").setFloValue(9.8667f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_top_9").setFloValue(15.3846f);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_1").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_2").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_3").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_4").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_5").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_min_6").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_1").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_2").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_3").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_4").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_5").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplaysizeandlocation_value_max_6").setValue(160);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_1").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_2").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_3").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_4").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_5").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_innercolor_6").setColor("00000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_1").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_2").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_3").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_4").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_5").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_style_back_outercolor_6").setColor("ff000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_1").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_2").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_3").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_4").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_5").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_start_6").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_1").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_2").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_3").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_4").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_5").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplayconfiguration_end_6").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_color_1").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_color_2").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_color_3").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_color_4").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_color_5").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_color_6").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_size_1").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_size_2").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_size_3").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_size_4").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_size_5").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_size_6").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_position_1").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_position_2").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_position_3").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_position_4").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_position_5").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_title_position_6").setValue(35);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_color_1").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_color_2").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_color_3").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_color_4").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_color_5").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_color_6").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_show_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_show_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_show_3").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_show_4").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_show_5").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_show_6").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_size_1").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_size_2").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_size_3").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_size_4").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_size_5").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_size_6").setValue(12);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_position_1").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_position_2").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_position_3").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_position_4").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_position_5").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_value_position_6").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_color_1").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_color_2").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_color_3").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_color_4").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_color_5").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_color_6").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_size_1").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_size_2").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_size_3").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_size_4").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_size_5").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_size_6").setValue(7);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_ver_1").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_ver_2").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_ver_3").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_ver_4").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_ver_5").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_ver_6").setValue(50);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_hor_1").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_hor_2").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_hor_3").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_hor_4").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_hor_5").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_units_hor_6").setValue(75);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_width_1").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_width_2").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_width_3").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_width_4").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_width_5").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_width_6").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_height_1").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_height_2").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_height_3").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_height_4").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_height_5").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_height_6").setValue(74);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_major_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_width_1").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_width_2").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_width_3").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_width_4").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_width_5").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_width_6").setValue(10);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_height_1").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_height_2").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_height_3").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_height_4").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_height_5").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_height_6").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_minor_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_show_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_show_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_show_3").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_show_4").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_show_5").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_show_6").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_1").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_2").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_3").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_4").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_5").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_rotate_6").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_size_1").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_size_2").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_size_3").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_size_4").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_size_5").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_size_6").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_1").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_2").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_3").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_4").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_5").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_lable_offset_6").setValue(85);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_3").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_4").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_5").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_show_6").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_1").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_2").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_3").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_4").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_5").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_width_6").setValue(4);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_1").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_2").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_3").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_4").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_5").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_length_6").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_1").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_2").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_3").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_4").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_5").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_color_6").setColor("fe9002");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_1").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_2").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_3").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_4").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_5").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_pointer_rad_6").setValue(5);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_center_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_center_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_center_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_center_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_center_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_center_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_visible_1").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_visible_2").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_visible_3").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_visible_4").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_visible_5").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_visible_6").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_1").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_2").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_3").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_4").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_5").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_startAngle_6").setValue(0);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_1").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_2").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_3").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_4").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_5").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_endAngle_6").setValue(360);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_color_1").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_color_2").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_color_3").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_color_4").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_color_5").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_range_color_6").setColor("d63636");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_1").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_2").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_3").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_4").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_5").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_color_6").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_1").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_2").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_3").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_4").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_5").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_back_rad_6").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_1").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_2").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_3").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_4").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_5").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_color_6").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_1").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_2").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_3").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_4").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_5").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_size_6").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_1").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_2").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_3").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_4").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_5").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_title_position_6").setValue(40);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_3").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_4").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_5").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_show_6").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_1").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_2").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_3").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_4").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_5").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_size_6").setValue(18);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_1").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_2").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_3").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_4").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_5").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_value_position_6").setValue(60);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_1").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_2").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_3").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_4").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_5").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_color_6").setColor("757476");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_1").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_2").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_3").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_4").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_5").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_size_6").setValue(8);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_1").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_2").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_3").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_4").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_5").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_units_position_6").setValue(73);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_1").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_2").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_3").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_4").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_5").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_pointer_width_6").setValue(2);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_3").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_4").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_5").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_show_6").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_1").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_2").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_3").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_4").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_5").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_two_range_color_6").setColor("00a6ff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_1").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_2").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_3").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_4").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_5").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_inner_color_6").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_1").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_2").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_3").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_4").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_5").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_outer_color_6").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_1").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_2").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_3").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_4").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_5").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_back_rad_6").setValue(100);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_1").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_2").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_3").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_4").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_5").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_size_6").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_1").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_2").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_3").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_4").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_5").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_title_position_6").setValue(34);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_3").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_4").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_5").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_show_6").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_1").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_2").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_3").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_4").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_5").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_size_6").setValue(23);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_1").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_2").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_3").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_4").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_5").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_value_position_6").setValue(63);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_1").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_2").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_3").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_4").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_5").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_color_6").setColor("ffffffff");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_1").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_2").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_3").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_4").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_5").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_size_6").setValue(14);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_1").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_2").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_3").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_4").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_5").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_units_position_6").setValue(80);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_1").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_2").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_3").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_4").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_5").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("dashboardsdisplay_three_frame_color_6").setColor("000000");
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_isRemoveDisplay_1").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_isRemoveDisplay_2").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_isRemoveDisplay_3").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_isRemoveDisplay_4").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_isRemoveDisplay_5").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("display_isRemoveDisplay_6").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);

            initDisplaysixtonine(7);
            initDisplaysixtonine(8);
            initDisplaysixtonine(9);


        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initView() {
        mRadioButton_obd = (RadioButton) findViewById(R.id.btn_main_obd);
        mRadioButton_special = (RadioButton) findViewById(R.id.btn_main_special);
        mRadioButton_persional = (RadioButton) findViewById(R.id.btn_main_persional);
        mRadioButton_obd.setOnClickListener(this);
        mRadioButton_special.setOnClickListener(this);
        mRadioButton_persional.setOnClickListener(this);
        //Fragment初始化
        mOBDFragment = new MainOBDFragment();
        mSpecialFragment = new MainSpecialFragment();
        mPersionalFragment = new MainPersionalFragment();
        //初始页面设置为OBD页面
        startFragmentAdd(mOBDFragment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_obd:
                //第一个不可点后两个可以点击
                mRadioButton_obd.setClickable(false);
                mRadioButton_special.setClickable(true);
                mRadioButton_persional.setClickable(true);

                startFragmentAdd(mOBDFragment, R.anim.slide_left_mid, R.anim.slide_mid_right);

                //设置页数在第一页
                page = 1;
                break;
            case R.id.btn_main_special:
                //第二个不可点后两个可以点击
                mRadioButton_obd.setClickable(true);
                mRadioButton_special.setClickable(false);
                mRadioButton_persional.setClickable(true);

                if (page == 1) {
                    startFragmentAdd(mSpecialFragment, R.anim.slide_right_mid, R.anim.slide_mid_left);
                }
                if (page == 3) {
                    startFragmentAdd(mSpecialFragment, R.anim.slide_left_mid, R.anim.slide_mid_right);
                }


                //设置页数在第二页
                page = 2;
                break;
            case R.id.btn_main_persional:
                //第三个不可点后两个可以点击
                mRadioButton_obd.setClickable(true);
                mRadioButton_special.setClickable(true);
                mRadioButton_persional.setClickable(false);

                startFragmentAdd(mPersionalFragment, R.anim.slide_right_mid, R.anim.slide_mid_left);


                //设置页数在第三页
                page = 3;
                break;
        }
    }
    //替换fragment方法 带动画
    private void startFragmentAdd(Fragment fragment, int anim_in, int anim_out) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        //设置动画  必须要在add  remove  replace 之前调用
        fragmentTransaction.setCustomAnimations(anim_in, anim_out);
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.frame_main_replace, fragment).commit();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment).add(R.id.frame_main_replace, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment).commit();
            }
            current_fragment = fragment;
        }
    }


    //替换fragment方法 不带动画
    private void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.frame_main_replace, fragment).commit();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment).add(R.id.frame_main_replace, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment).commit();
            }
            current_fragment = fragment;
        }
    }




    //自定义添加仪表盘数据库          新的仪表盘  需要有一个属性
    private static void initDisplaysixtonine(int display_count) {
        OBDL obdl = new OBDL();
        //        //还要再多一个Style   display_style_
        obdl.setId(null).setKey("display_style_" + display_count).setValue(0);
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


}
