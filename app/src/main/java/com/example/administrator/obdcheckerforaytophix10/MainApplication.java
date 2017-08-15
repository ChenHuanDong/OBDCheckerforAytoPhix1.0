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
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth", (int) 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_one", (float) 6.667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_one", (float) 1.748);
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


    }




}
