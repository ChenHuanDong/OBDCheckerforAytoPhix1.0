package com.example.administrator.obdcheckerforaytophix10;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.io.File;
import java.util.List;

/**
 * Created by CHD on 2017/8/7.
 */

public class MainApplication extends Application {

    private static Context sContext;
    private static DaoMaster sDaoMaster;
    private static DaoSession sDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        SPUtil.put(this, "screenWidth", ScreenUtils.getScreenWidth(this));
        SPUtil.put(this, "screenHeight", ScreenUtils.getScreenHeight(this));

//        //判断是经典还是自定义模式
//        SPUtil.put(this, "dashboardsisclassic", true);
//
//
//        //6个仪表盘Style
//        SPUtil.put(this, "display_style_1", 0);


        //6个仪表盘的初始大小位置设置     高度是占572.0f  的百分比
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_10", (int) 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_10", (float) 10.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_10", (float) 5.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_11", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_11", (float) 11.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_11", (float) 6.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_12", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_12", (float) 12.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_12", (float) 7.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_13", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_13", (float) 13.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_13", (float) 8.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_14", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_14", (float) 14.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_14", (float) 9.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_15", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_15", (float) 15.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_15", (float) 10.0f);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_7", (int) 59);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_7", (float) 20.5333);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_7", (float) 3.1469);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_8", (int) 59);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_8", (float) 20.5333);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_8", (float) 51.7483);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_10", (int) 80);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_10", (float) 9.8667);
//        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_10", (float) 15.3846);
        //DC    仪表盘的刻度的最大值最小值
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_10", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_11", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_12", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_13", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_14", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_15", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_10", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_11", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_12", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_13", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_14", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_15", 160);

        //第一个仪表盘的颜色
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_10", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_10", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_11", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_11", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_12", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_12", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_13", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_13", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_14", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_14", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_15", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_15", "ff000000");
        //第一页六个仪表盘的初始和结束角度
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_10", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_10", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_11", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_11", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_12", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_12", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_13", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_13", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_14", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_14", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_15", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_15", (int) 360);
        //标题颜色
        SPUtil.put(this, "dashboardsdisplay_title_color_10", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_11", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_12", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_13", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_14", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_15", "fe9002");
        //标题字体大小
        SPUtil.put(this, "dashboardsdisplay_title_size_10", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_11", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_12", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_13", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_14", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_15", 10);
        //标题位置
        SPUtil.put(this, "dashboardsdisplay_title_position_10", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_11", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_12", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_13", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_14", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_15", 35);
        //数值颜色
        SPUtil.put(this, "dashboardsdisplay_value_color_10", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_11", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_12", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_13", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_14", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_15", "fe9002");
        //数值是否存在
        SPUtil.put(this, "dashboardsdisplay_value_show_10", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_11", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_12", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_13", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_14", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_15", true);
        //数值字体大小
        SPUtil.put(this, "dashboardsdisplay_value_size_10", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_11", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_12", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_13", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_14", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_15", 12);
        //value  position
        SPUtil.put(this, "dashboardsdisplay_value_position_10", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_11", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_12", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_13", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_14", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_15", 100);
        //units color
        SPUtil.put(this, "dashboardsdisplay_units_color_10", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_11", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_12", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_13", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_14", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_15", "fe9002");
        //units size
        SPUtil.put(this, "dashboardsdisplay_units_size_10", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_11", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_12", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_13", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_14", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_15", 7);
        //units ver
        SPUtil.put(this, "dashboardsdisplay_units_ver_10", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_11", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_12", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_13", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_14", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_15", 50);
        //units hor
        SPUtil.put(this, "dashboardsdisplay_units_hor_10", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_11", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_12", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_13", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_14", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_15", 75);
        //  长指针宽度
        SPUtil.put(this, "dashboardsdisplay_major_width_10", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_11", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_12", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_13", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_14", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_15", 10);
        //长指针高度
        SPUtil.put(this, "dashboardsdisplay_major_height_10", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_11", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_12", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_13", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_14", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_15", 74);
        //长指针颜色
        SPUtil.put(this, "dashboardsdisplay_major_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_15", "ffffffff");
        //短指针宽度
        SPUtil.put(this, "dashboardsdisplay_minor_width_10", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_11", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_12", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_13", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_14", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_15", 10);
        //短指针高度
        SPUtil.put(this, "dashboardsdisplay_minor_height_10", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_11", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_12", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_13", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_14", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_15", 80);
        //短指针颜色
        SPUtil.put(this, "dashboardsdisplay_minor_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_15", "ffffffff");
        //刻度文字是都显示
        SPUtil.put(this, "dashboardsdisplay_lable_show_10", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_11", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_12", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_13", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_14", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_15", true);
        //判断文字是否旋转
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_10", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_11", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_12", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_13", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_14", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_15", false);
        //设置文字大小
        SPUtil.put(this, "dashboardsdisplay_lable_size_10", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_11", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_12", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_13", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_14", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_15", 8);
        //设置文字缩放
        SPUtil.put(this, "dashboardsdisplay_lable_offset_10", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_11", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_12", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_13", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_14", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_15", 85);
        //指针是否显示
        SPUtil.put(this, "dashboardsdisplay_pointer_show_10", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_11", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_12", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_13", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_14", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_15", true);
        //指针的宽度
        SPUtil.put(this, "dashboardsdisplay_pointer_width_10", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_11", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_12", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_13", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_14", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_15", 4);
        //指针的高度
        SPUtil.put(this, "dashboardsdisplay_pointer_length_10", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_11", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_12", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_13", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_14", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_15", 40);
        //指针颜色
        SPUtil.put(this, "dashboardsdisplay_pointer_color_10", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_11", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_12", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_13", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_14", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_15", "fe9002");
        //指针中心圆半径
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_10", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_11", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_12", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_13", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_14", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_15", 5);
        //中心圆  颜色
        SPUtil.put(this, "dashboardsdisplay_center_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_15", "ffffffff");
        //range visible
        SPUtil.put(this, "dashboardsdisplay_range_visible_10", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_11", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_12", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_13", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_14", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_15", false);
        //range  StartAngle
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_10", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_11", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_12", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_13", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_14", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_15", 0);
        //range  EndAngle
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_10", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_11", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_12", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_13", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_14", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_15", 360);
        //range  color
        SPUtil.put(this, "dashboardsdisplay_range_color_10", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_11", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_12", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_13", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_14", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_15", "d63636");


        //  Style 2 back color
        SPUtil.put(this, "dashboardsdisplay_two_back_color_10", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_11", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_12", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_13", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_14", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_15", "00a6ff");
        //Style 2 back rad
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_10", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_11", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_12", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_13", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_14", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_15", 60);
        //Style 2 title color
        SPUtil.put(this, "dashboardsdisplay_two_title_color_10", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_11", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_12", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_13", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_14", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_15", "757476");
        //Style 2 title size
        SPUtil.put(this, "dashboardsdisplay_two_title_size_10", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_11", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_12", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_13", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_14", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_15", 8);
        //style 2 title position
        SPUtil.put(this, "dashboardsdisplay_two_title_position_10", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_11", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_12", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_13", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_14", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_15", 40);
        //style 2 value show
        SPUtil.put(this, "dashboardsdisplay_two_value_show_10", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_11", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_12", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_13", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_14", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_15", true);
        //style 2 calue color
        SPUtil.put(this, "dashboardsdisplay_two_value_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_15", "ffffffff");
        //style 2 value size
        SPUtil.put(this, "dashboardsdisplay_two_value_size_10", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_11", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_12", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_13", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_14", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_15", 18);
        //style 2 value position
        SPUtil.put(this, "dashboardsdisplay_two_value_position_10", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_11", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_12", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_13", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_14", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_15", 60);
        //style  2  units color
        SPUtil.put(this, "dashboardsdisplay_two_units_color_10", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_11", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_12", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_13", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_14", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_15", "757476");
        //style 2 units size
        SPUtil.put(this, "dashboardsdisplay_two_units_size_10", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_11", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_12", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_13", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_14", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_15", 8);
        //syule 2 units position
        SPUtil.put(this, "dashboardsdisplay_two_units_position_10", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_11", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_12", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_13", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_14", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_15", 73);
        //syule 2 pointer color
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_15", "ffffffff");
        //style 2 pointer width
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_10", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_11", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_12", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_13", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_14", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_15", 2);
        //style 2 range show
        SPUtil.put(this, "dashboardsdisplay_two_range_show_10", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_11", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_12", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_13", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_14", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_15", true);
        //style 2 range color
        SPUtil.put(this, "dashboardsdisplay_two_range_color_10", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_11", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_12", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_13", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_14", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_15", "00a6ff");
        //style 3 inner color
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_10", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_11", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_12", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_13", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_14", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_15", "000000");
        //style 3 outer color
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_10", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_11", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_12", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_13", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_14", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_15", "000000");
        //style 3 back rad
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_10", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_11", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_12", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_13", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_14", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_15", 100);
        //style 3 title color
        SPUtil.put(this, "dashboardsdisplay_three_title_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_15", "ffffffff");
        //style 3 title size
        SPUtil.put(this, "dashboardsdisplay_three_title_size_10", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_11", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_12", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_13", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_14", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_15", 14);
        //style 3 title position
        SPUtil.put(this, "dashboardsdisplay_three_title_position_10", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_11", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_12", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_13", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_14", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_15", 34);
        //style 3 value show
        SPUtil.put(this, "dashboardsdisplay_three_value_show_10", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_11", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_12", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_13", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_14", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_15", true);
        //style 3 value color
        SPUtil.put(this, "dashboardsdisplay_three_value_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_15", "ffffffff");
        //style 3 calue size
        SPUtil.put(this, "dashboardsdisplay_three_value_size_10", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_11", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_12", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_13", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_14", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_15", 23);
        //style 3 value position
        SPUtil.put(this, "dashboardsdisplay_three_value_position_10", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_11", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_12", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_13", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_14", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_15", 63);
        //style 3 units color
        SPUtil.put(this, "dashboardsdisplay_three_units_color_10", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_11", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_12", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_13", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_14", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_15", "ffffffff");
        //style 3 units size
        SPUtil.put(this, "dashboardsdisplay_three_units_size_10", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_11", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_12", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_13", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_14", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_15", 14);
        //style 3 units position
        SPUtil.put(this, "dashboardsdisplay_three_units_position_10", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_11", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_12", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_13", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_14", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_15", 80);
        //style 3 frame color
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_10", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_11", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_12", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_13", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_14", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_15", "000000");

        //是否Remove  Display
        SPUtil.put(this, "display_isRemoveDisplay_10", false);
        SPUtil.put(this, "display_isRemoveDisplay_11", false);
        SPUtil.put(this, "display_isRemoveDisplay_12", false);
        SPUtil.put(this, "display_isRemoveDisplay_13", false);
        SPUtil.put(this, "display_isRemoveDisplay_14", false);
        SPUtil.put(this, "display_isRemoveDisplay_15", false);

    }

    //对外提供获取DaoMaster
    public static DaoMaster getDaoMaster() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(sContext, "Settings.db", null);
        sDaoMaster = new DaoMaster(helper.getWritableDb());
        return sDaoMaster;
    }

    public static DaoSession getDaoSession() {

        if (sDaoSession == null) {
            if (sDaoMaster == null) {
                sDaoMaster = getDaoMaster();
            }
        }
        sDaoSession = sDaoMaster.newSession();

        return sDaoSession;
    }


}
