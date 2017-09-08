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


        //6个仪表盘的初始大小位置设置     高度是占572.0f  的百分比
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_1", (int) 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_1", (float) 6.667f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_1", (float) 5.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_2", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_2", (float) 11.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_2", (float) 6.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_3", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_3", (float) 12.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_3", (float) 7.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_4", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_4", (float) 13.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_4", (float) 8.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_5", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_5", (float) 14.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_5", (float) 9.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_6", 40);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_6", (float) 15.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_6", (float) 10.0f);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_7", (int) 59);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_7", (float) 20.5333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_7", (float) 3.1469);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_8", (int) 59);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_8", (float) 20.5333);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_8", (float) 51.7483);
        SPUtil.put(this, "dashboardsdisplaysizeandlocationwidth_9", (int) 80);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_left_9", (float) 9.8667);
        SPUtil.put(this, "dashboardsdisplaysizeandlocation_top_9", (float) 15.3846);




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
