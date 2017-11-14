package com.example.administrator.obdcheckerforaytophix10.logs;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/10/17.
 */

public class ThreadPlayAgain extends Thread {

    public volatile boolean exit = false;
    private Context mContext;

    private int count = 0;

    private ArrayList<Integer> dataOne = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListOne").getDatas();
    private ArrayList<Integer> dataTwo = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListTwo").getDatas();
    private ArrayList<Integer> dataThree = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListThree").getDatas();
    private ArrayList<Integer> dataFour = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListFour").getDatas();


    @Override
    public void run() {
        super.run();

        while (!exit) {

            for (int i = 0; i < dataOne.size() ; i++) {

                count++;
                Intent intent = new Intent("chartplayagain");
                intent.putExtra("count", count);
                intent.putExtra("yValueOne", dataOne.get(count - 1));
                intent.putExtra("yValueTwo", dataTwo.get(count - 1));
                intent.putExtra("yValueThree", dataThree.get(count - 1));
                intent.putExtra("yValueFour", dataFour.get(count - 1));
                mContext.sendBroadcast(intent);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //!!!!!!!!!!!!
                if (count == dataOne.size() - 1){
                    exit = true;
                }


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
