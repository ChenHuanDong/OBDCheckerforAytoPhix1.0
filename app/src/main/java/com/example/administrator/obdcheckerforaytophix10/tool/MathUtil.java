package com.example.administrator.obdcheckerforaytophix10.tool;

/**
 * Created by CHD on 2017/8/9.
 */

public class MathUtil {
    //把String转化成int
    public static int stringToInt(String x) {
        int a = 0;
        if (!x.equals("")) {
            a = Integer.parseInt(x);
        } else {
            a = 0;
        }
        return a;
    }

    //把String转化成float
    public static float stringToFloat(String x) {
        float a = 0;
        if (!x.equals("")) {
            a = Float.parseFloat(x);
        } else {
            a = (float) 0.0;
        }
        return a;
    }

}
