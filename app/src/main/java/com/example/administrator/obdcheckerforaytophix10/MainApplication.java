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
        //6个仪表盘的初始大小位置设置     高度是占572.0f  的百分比
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_one", (int) 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_one", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_one", (float) 1.748);
        //第一个仪表盘的颜色
        SPUtil.put(this, "dashboardsdisplay_style_back_innercolor", "00000000");
        SPUtil.put(this, "dashboardsdisplay_style_back_outercolor", "ff000000");




        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_two", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_two", (float) 53.333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_two", (float) 1.748);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_three", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_three", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_three", (float) 34.266);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_four", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_four", (float) 53.333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_four", (float) 34.266);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_five", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_five", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_five", (float) 66.783);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_six", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_six", (float) 53.333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_six", (float) 66.783);
        //6个仪表盘的刻度初始数值
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_one", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_one", (int) 160);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_two", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_two", (int) 160);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_three", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_three", (int) 160);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_four", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_four", (int) 160);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_five", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_five", (int) 160);
        SPUtil.put(this, "dashboardsdisplayconfiguration_start_six", (int) 0);
        SPUtil.put(this, "dashboardsdisplayconfiguration_end_six", (int) 160);

        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_seven", (int) 59);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_seven", (float) 20.5333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_seven", (float) 3.1469);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_eight", (int) 59);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_eight", (float) 20.5333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_eight", (float) 51.7483);

        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_nine", (int) 80);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_nine", (float) 9.8667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_nine", (float) 15.3846);


    }


}
