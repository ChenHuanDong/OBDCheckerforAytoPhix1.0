package com.example.administrator.obdcheckerforaytophix10.logs;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import java.util.Random;

/**
 * Created by CHD on 2017/10/10.
 */

public class ThreadSafe extends Thread {

    public volatile boolean exit = false;
    private Context mContext;
    //计数
    private int i = 0;
    private Random rand = new Random();




    @Override
    public void run() {

        while (!exit) {

            i++;
            Intent intent = new Intent("changeChartData");
            intent.putExtra("count", i);
            intent.putExtra("yValue", rand.nextInt(100));
            mContext.sendBroadcast(intent);

            try {
                ThreadSafe.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
