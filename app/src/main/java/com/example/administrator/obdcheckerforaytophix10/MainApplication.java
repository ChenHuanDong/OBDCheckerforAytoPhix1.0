package com.example.administrator.obdcheckerforaytophix10;

import android.app.Application;

import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/7.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SPUtil.put(this, "screenWidth", ScreenUtils.getScreenWidth(this));
        SPUtil.put(this, "screenHeight", ScreenUtils.getScreenHeight(this));

    }


}
