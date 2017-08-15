package com.example.administrator.obdcheckerforaytophix10.tool;

/**
 * Created by CHD on 2017/8/7.
 */

public class ConversionUtil {

    //自定义转换     输入的就是多少的x123
    public static float myWantValue(float width, float a) {
        float want = (float) (((float) a * (float) width) / (float) 375.0);
        return want;
    }



}
