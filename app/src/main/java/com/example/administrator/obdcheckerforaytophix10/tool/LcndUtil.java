package com.example.administrator.obdcheckerforaytophix10.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.io.File;

/**
 * Created by CHD on 2017/8/11.
 */

public class LcndUtil {


    public static Typeface getfont(Context c) {
        final String FONT_DIGITAL_7 = "fonts" + File.separator
                + "lcnd.ttf";//定义字体文件路径。
        AssetManager assets = c.getAssets();
        Typeface font = Typeface.createFromAsset(assets, FONT_DIGITAL_7);
        return font;
    }


}
