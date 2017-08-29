package com.example.administrator.obdcheckerforaytophix10;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.io.File;

/**
 * Created by CHD on 2017/8/7.
 */

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        SPUtil.put(this, "screenWidth", ScreenUtils.getScreenWidth(this));
        SPUtil.put(this, "screenHeight", ScreenUtils.getScreenHeight(this));
        //判断是经典还是自定义模式
        SPUtil.put(this, "dashboardsisclassic", true);


        //6个仪表盘Style
        SPUtil.put(this, "display_style_1", 0);


        //6个仪表盘的初始大小位置设置     高度是占572.0f  的百分比
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_1", (int) 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_1", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_1", (float) 1.748);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_2", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_2", (float) 53.333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_2", (float) 1.748);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_3", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_3", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_3", (float) 34.266);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_4", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_4", (float) 53.333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_4", (float) 34.266);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_5", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_5", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_5", (float) 66.783);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_6", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_6", (float) 53.333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_6", (float) 66.783);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_7", (int) 59);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_7", (float) 20.5333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_7", (float) 3.1469);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_8", (int) 59);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_8", (float) 20.5333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_8", (float) 51.7483);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_9", (int) 80);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_9", (float) 9.8667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_9", (float) 15.3846);
        //DC    仪表盘的刻度的最大值最小值
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_1", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_2", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_3", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_4", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_5", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_min_6", 0);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_1", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_2", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_3", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_4", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_5", 160);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_value_max_6", 160);

        //第一个仪表盘的颜色
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_1", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_1", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_2", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_2", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_3", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_3", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_4", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_4", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_5", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_5", "ff000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor_6", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor_6", "ff000000");
        //第一页六个仪表盘的初始和结束角度
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_1", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_1", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_2", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_2", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_3", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_3", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_4", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_4", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_5", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_5", (int) 360);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_6", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_6", (int) 360);
        //标题颜色
        SPUtil.put(this, "dashboardsdisplay_title_color_1", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_2", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_3", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_4", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_5", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_title_color_6", "fe9002");
        //标题字体大小
        SPUtil.put(this, "dashboardsdisplay_title_size_1", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_2", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_3", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_4", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_5", 10);
        SPUtil.put(this, "dashboardsdisplay_title_size_6", 10);
        //标题位置
        SPUtil.put(this, "dashboardsdisplay_title_position_1", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_2", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_3", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_4", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_5", 35);
        SPUtil.put(this, "dashboardsdisplay_title_position_6", 35);
        //数值颜色
        SPUtil.put(this, "dashboardsdisplay_value_color_1", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_2", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_3", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_4", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_5", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_value_color_6", "fe9002");
        //数值是否存在
        SPUtil.put(this, "dashboardsdisplay_value_show_1", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_2", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_3", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_4", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_5", true);
        SPUtil.put(this, "dashboardsdisplay_value_show_6", true);
        //数值字体大小
        SPUtil.put(this, "dashboardsdisplay_value_size_1", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_2", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_3", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_4", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_5", 12);
        SPUtil.put(this, "dashboardsdisplay_value_size_6", 12);
        //value  position
        SPUtil.put(this, "dashboardsdisplay_value_position_1", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_2", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_3", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_4", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_5", 100);
        SPUtil.put(this, "dashboardsdisplay_value_position_6", 100);
        //units color
        SPUtil.put(this, "dashboardsdisplay_units_color_1", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_2", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_3", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_4", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_5", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_units_color_6", "fe9002");
        //units size
        SPUtil.put(this, "dashboardsdisplay_units_size_1", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_2", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_3", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_4", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_5", 7);
        SPUtil.put(this, "dashboardsdisplay_units_size_6", 7);
        //units ver
        SPUtil.put(this, "dashboardsdisplay_units_ver_1", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_2", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_3", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_4", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_5", 50);
        SPUtil.put(this, "dashboardsdisplay_units_ver_6", 50);
        //units hor
        SPUtil.put(this, "dashboardsdisplay_units_hor_1", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_2", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_3", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_4", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_5", 75);
        SPUtil.put(this, "dashboardsdisplay_units_hor_6", 75);
        //  长指针宽度
        SPUtil.put(this, "dashboardsdisplay_major_width_1", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_2", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_3", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_4", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_5", 10);
        SPUtil.put(this, "dashboardsdisplay_major_width_6", 10);
        //长指针高度
        SPUtil.put(this, "dashboardsdisplay_major_height_1", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_2", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_3", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_4", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_5", 74);
        SPUtil.put(this, "dashboardsdisplay_major_height_6", 74);
        //长指针颜色
        SPUtil.put(this, "dashboardsdisplay_major_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_major_color_6", "ffffffff");
        //短指针宽度
        SPUtil.put(this, "dashboardsdisplay_minor_width_1", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_2", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_3", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_4", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_5", 10);
        SPUtil.put(this, "dashboardsdisplay_minor_width_6", 10);
        //短指针高度
        SPUtil.put(this, "dashboardsdisplay_minor_height_1", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_2", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_3", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_4", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_5", 80);
        SPUtil.put(this, "dashboardsdisplay_minor_height_6", 80);
        //短指针颜色
        SPUtil.put(this, "dashboardsdisplay_minor_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_minor_color_6", "ffffffff");
        //刻度文字是都显示
        SPUtil.put(this, "dashboardsdisplay_lable_show_1", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_2", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_3", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_4", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_5", true);
        SPUtil.put(this, "dashboardsdisplay_lable_show_6", true);
        //判断文字是否旋转
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_1", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_2", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_3", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_4", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_5", false);
        SPUtil.put(this, "dashboardsdisplay_lable_rotate_6", false);
        //设置文字大小
        SPUtil.put(this, "dashboardsdisplay_lable_size_1", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_2", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_3", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_4", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_5", 8);
        SPUtil.put(this, "dashboardsdisplay_lable_size_6", 8);
        //设置文字缩放
        SPUtil.put(this, "dashboardsdisplay_lable_offset_1", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_2", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_3", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_4", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_5", 85);
        SPUtil.put(this, "dashboardsdisplay_lable_offset_6", 85);
        //指针是否显示
        SPUtil.put(this, "dashboardsdisplay_pointer_show_1", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_2", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_3", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_4", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_5", true);
        SPUtil.put(this, "dashboardsdisplay_pointer_show_6", true);
        //指针的宽度
        SPUtil.put(this, "dashboardsdisplay_pointer_width_1", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_2", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_3", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_4", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_5", 4);
        SPUtil.put(this, "dashboardsdisplay_pointer_width_6", 4);
        //指针的高度
        SPUtil.put(this, "dashboardsdisplay_pointer_length_1", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_2", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_3", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_4", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_5", 40);
        SPUtil.put(this, "dashboardsdisplay_pointer_length_6", 40);
        //指针颜色
        SPUtil.put(this, "dashboardsdisplay_pointer_color_1", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_2", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_3", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_4", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_5", "fe9002");
        SPUtil.put(this, "dashboardsdisplay_pointer_color_6", "fe9002");
        //指针中心圆半径
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_1", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_2", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_3", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_4", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_5", 5);
        SPUtil.put(this, "dashboardsdisplay_pointer_rad_6", 5);
        //中心圆  颜色
        SPUtil.put(this, "dashboardsdisplay_center_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_center_color_6", "ffffffff");
        //range visible
        SPUtil.put(this, "dashboardsdisplay_range_visible_1", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_2", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_3", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_4", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_5", false);
        SPUtil.put(this, "dashboardsdisplay_range_visible_6", false);
        //range  StartAngle
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_1", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_2", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_3", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_4", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_5", 0);
        SPUtil.put(this, "dashboardsdisplay_range_startAngle_6", 0);
        //range  EndAngle
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_1", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_2", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_3", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_4", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_5", 360);
        SPUtil.put(this, "dashboardsdisplay_range_endAngle_6", 360);
        //range  color
        SPUtil.put(this, "dashboardsdisplay_range_color_1", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_2", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_3", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_4", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_5", "d63636");
        SPUtil.put(this, "dashboardsdisplay_range_color_6", "d63636");


        //  Style 2 back color
        SPUtil.put(this, "dashboardsdisplay_two_back_color_1", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_2", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_3", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_4", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_5", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_back_color_6", "00a6ff");
        //Style 2 back rad
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_1", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_2", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_3", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_4", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_5", 60);
        SPUtil.put(this, "dashboardsdisplay_two_back_rad_6", 60);
        //Style 2 title color
        SPUtil.put(this, "dashboardsdisplay_two_title_color_1", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_2", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_3", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_4", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_5", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_title_color_6", "757476");
        //Style 2 title size
        SPUtil.put(this, "dashboardsdisplay_two_title_size_1", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_2", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_3", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_4", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_5", 8);
        SPUtil.put(this, "dashboardsdisplay_two_title_size_6", 8);
        //style 2 title position
        SPUtil.put(this, "dashboardsdisplay_two_title_position_1", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_2", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_3", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_4", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_5", 40);
        SPUtil.put(this, "dashboardsdisplay_two_title_position_6", 40);
        //style 2 value show
        SPUtil.put(this, "dashboardsdisplay_two_value_show_1", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_2", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_3", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_4", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_5", true);
        SPUtil.put(this, "dashboardsdisplay_two_value_show_6", true);
        //style 2 calue color
        SPUtil.put(this, "dashboardsdisplay_two_value_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_value_color_6", "ffffffff");
        //style 2 value size
        SPUtil.put(this, "dashboardsdisplay_two_value_size_1", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_2", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_3", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_4", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_5", 18);
        SPUtil.put(this, "dashboardsdisplay_two_value_size_6", 18);
        //style 2 value position
        SPUtil.put(this, "dashboardsdisplay_two_value_position_1", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_2", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_3", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_4", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_5", 60);
        SPUtil.put(this, "dashboardsdisplay_two_value_position_6", 60);
        //style  2  units color
        SPUtil.put(this, "dashboardsdisplay_two_units_color_1", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_2", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_3", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_4", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_5", "757476");
        SPUtil.put(this, "dashboardsdisplay_two_units_color_6", "757476");
        //style 2 units size
        SPUtil.put(this, "dashboardsdisplay_two_units_size_1", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_2", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_3", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_4", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_5", 8);
        SPUtil.put(this, "dashboardsdisplay_two_units_size_6", 8);
        //syule 2 units position
        SPUtil.put(this, "dashboardsdisplay_two_units_position_1", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_2", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_3", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_4", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_5", 73);
        SPUtil.put(this, "dashboardsdisplay_two_units_position_6", 73);
        //syule 2 pointer color
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_two_pointer_color_6", "ffffffff");
        //style 2 pointer width
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_1", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_2", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_3", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_4", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_5", 2);
        SPUtil.put(this, "dashboardsdisplay_two_pointer_width_6", 2);
        //style 2 range show
        SPUtil.put(this, "dashboardsdisplay_two_range_show_1", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_2", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_3", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_4", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_5", true);
        SPUtil.put(this, "dashboardsdisplay_two_range_show_6", true);
        //style 2 range color
        SPUtil.put(this, "dashboardsdisplay_two_range_color_1", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_2", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_3", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_4", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_5", "00a6ff");
        SPUtil.put(this, "dashboardsdisplay_two_range_color_6", "00a6ff");
        //style 3 inner color
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_1", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_2", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_3", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_4", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_5", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_inner_color_6", "000000");
        //style 3 outer color
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_1", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_2", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_3", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_4", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_5", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_outer_color_6", "000000");
        //style 3 back rad
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_1", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_2", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_3", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_4", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_5", 100);
        SPUtil.put(this, "dashboardsdisplay_three_back_rad_6", 100);
        //style 3 title color
        SPUtil.put(this, "dashboardsdisplay_three_title_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_title_color_6", "ffffffff");
        //style 3 title size
        SPUtil.put(this, "dashboardsdisplay_three_title_size_1", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_2", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_3", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_4", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_5", 14);
        SPUtil.put(this, "dashboardsdisplay_three_title_size_6", 14);
        //style 3 title position
        SPUtil.put(this, "dashboardsdisplay_three_title_position_1", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_2", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_3", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_4", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_5", 34);
        SPUtil.put(this, "dashboardsdisplay_three_title_position_6", 34);
        //style 3 value show
        SPUtil.put(this, "dashboardsdisplay_three_value_show_1", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_2", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_3", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_4", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_5", true);
        SPUtil.put(this, "dashboardsdisplay_three_value_show_6", true);
        //style 3 value color
        SPUtil.put(this, "dashboardsdisplay_three_value_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_value_color_6", "ffffffff");
        //style 3 calue size
        SPUtil.put(this, "dashboardsdisplay_three_value_size_1", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_2", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_3", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_4", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_5", 23);
        SPUtil.put(this, "dashboardsdisplay_three_value_size_6", 23);
        //style 3 value position
        SPUtil.put(this, "dashboardsdisplay_three_value_position_1", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_2", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_3", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_4", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_5", 63);
        SPUtil.put(this, "dashboardsdisplay_three_value_position_6", 63);
        //style 3 units color
        SPUtil.put(this, "dashboardsdisplay_three_units_color_1", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_2", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_3", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_4", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_5", "ffffffff");
        SPUtil.put(this, "dashboardsdisplay_three_units_color_6", "ffffffff");
        //style 3 units size
        SPUtil.put(this, "dashboardsdisplay_three_units_size_1", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_2", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_3", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_4", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_5", 14);
        SPUtil.put(this, "dashboardsdisplay_three_units_size_6", 14);
        //style 3 units position
        SPUtil.put(this, "dashboardsdisplay_three_units_position_1", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_2", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_3", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_4", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_5", 80);
        SPUtil.put(this, "dashboardsdisplay_three_units_position_6", 80);
        //style 3 frame color
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_1", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_2", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_3", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_4", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_5", "000000");
        SPUtil.put(this, "dashboardsdisplay_three_frame_color_6", "000000");

        //是否Remove  Display
        SPUtil.put(this, "display_isRemoveDisplay_1", false);
        SPUtil.put(this, "display_isRemoveDisplay_2", false);
        SPUtil.put(this, "display_isRemoveDisplay_3", false);
        SPUtil.put(this, "display_isRemoveDisplay_4", false);
        SPUtil.put(this, "display_isRemoveDisplay_5", false);
        SPUtil.put(this, "display_isRemoveDisplay_6", false);





    }


}
