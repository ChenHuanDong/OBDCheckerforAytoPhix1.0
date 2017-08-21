package com.example.administrator.obdcheckerforaytophix10.tool;

/**
 * Created by CHD on 2017/8/21.
 */

public class StringUtil {

    public static int getAlpha(int color) {
        return (color & 0xff000000) >>> 24;
    }

    public static int getRed(int color) {
        return (color & 0xff0000) >> 16;
    }

    public static int getGreen(int color) {
        return (color & 0x00ff00) >> 8;
    }

    public static int getBlue(int color) {
        return (color & 0x0000ff);
    }


}
