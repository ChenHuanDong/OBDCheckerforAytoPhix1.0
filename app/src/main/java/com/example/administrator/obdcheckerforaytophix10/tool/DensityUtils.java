package com.example.administrator.obdcheckerforaytophix10.tool;

/**
 * Created by CHD on 2017/10/24.
 */

public class DensityUtils {

    //字节数组转十六进制   （字符串转十六进制？）
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //十六进制转字符串
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    //-----------------------------------------------------------------------------------------

    // 十六进制转二进制
    public static String HToB(String a) {
        String b = Integer.toBinaryString(Integer.valueOf(toD(a, 16)));
        String c = "0";
        while (b.length() < 16) {
            b = c.concat(b);
        }
//        LogUtil.fussenLog().d("转换成二进制后的长度：" + b.length());
//        LogUtil.fussenLog().d("这个是转换成二进制的结果：" + b);
        return b;
    }

    //这个是把传入的长度是十六的二进制数  根据前两位判断返回PCBU
    public static String getPCBU(String a) {
        a = a.substring(0, 2);
        switch (a) {
            case "00":
                a = "P";
                break;
            case "01":
                a = "C";
                break;
            case "10":
                a = "B";
                break;
            case "11":
                a = "U";
                break;
        }
        return a;
    }

    //这个是最后的  直接调用的  传入长度是4的十六进制  然后返回 数字加
    public static String getAll(String a){
        //第一步 先变成二进制
        String b = HToB(a);
        //第二步 得到开头的 PCBU
        String c = getPCBU(b);
        //第三步 把前两位变成00
        b = b.substring(2);
        b = "00".concat(b);
        //第四步把补完位的  变成十六进制
        b = BToH(b);
        //第五步拼接
        String d = c.concat(b);
        return d;
    }


    // 二进制转十六进制
    public static String BToH(String a) {
        // 将二进制转为十进制再从十进制转为十六进制
        String b = Integer.toHexString(Integer.valueOf(toD(a, 2)));
        while (b.length()<4){
            b = "0".concat(b);
        }
        return b;
    }


    // 任意进制数转为十进制数
    public static String toD(String a, int b) {
        int r = 0;
        for (int i = 0; i < a.length(); i++) {
            r = (int) (r + formatting(a.substring(i, i + 1))
                    * Math.pow(b, a.length() - i - 1));
        }
        return String.valueOf(r);
    }


    // 将十六进制中的字母转为对应的数字
    public static int formatting(String a) {
        int i = 0;
        for (int u = 0; u < 10; u++) {
            if (a.equals(String.valueOf(u))) {
                i = u;
            }
        }
        if (a.equals("a")) {
            i = 10;
        }
        if (a.equals("b")) {
            i = 11;
        }
        if (a.equals("c")) {
            i = 12;
        }
        if (a.equals("d")) {
            i = 13;
        }
        if (a.equals("e")) {
            i = 14;
        }
        if (a.equals("f")) {
            i = 15;
        }
        return i;
    }



//    //十六进制转十进制   单个字节
//    public static int formatting(String a) {
//        int i = 0;
//        for (int u = 0; u < 10; u++) {
//            if (a.equals(String.valueOf(u))) {
//                i = u;
//            }
//        }
//        if (a.equals("a")) {
//            i = 10;
//        }
//        if (a.equals("b")) {
//            i = 11;
//        }
//        if (a.equals("c")) {
//            i = 12;
//        }
//        if (a.equals("d")) {
//            i = 13;
//        }
//        if (a.equals("e")) {
//            i = 14;
//        }
//        if (a.equals("f")) {
//            i = 15;
//        }
//        return i;
//    }
//
//    //根据传入的int型十进制   判断返回的是PCBU哪一个
//    public static String getPCBU(int a) {
//        //PCBU   总共16个数字   0~3 P    4~7 C    8~11 B     12~15 U
//        String pp = "P";
//        switch (a) {
//            case 4:
//                pp = "C";
//                break;
//            case 5:
//                pp = "C";
//                break;
//            case 6:
//                pp = "C";
//                break;
//            case 7:
//                pp = "C";
//                break;
//            case 8:
//                pp = "B";
//                break;
//            case 9:
//                pp = "B";
//                break;
//            case 10:
//                pp = "B";
//                break;
//            case 11:
//                pp = "B";
//                break;
//            case 12:
//                pp = "U";
//                break;
//            case 13:
//                pp = "U";
//                break;
//            case 14:
//                pp = "U";
//                break;
//            case 15:
//                pp = "U";
//                break;
//        }
//        return pp;
//    }


}
