package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.logs.ThreadPlayAgain;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/10/9.
 */
//这个是真正实际的   回放的Fragment
//OBDLogsFileDetailFragment  这个是这个的父Fragment
public class LogsDetailChartFragment extends Fragment {

    private LineChart chratOne , chartTwo;

    private LineData mData, mData_one;
    private LineDataSet mDataSet_zero, mDataSet_one, mDataSet_two, mDataSet_three;
    private ArrayList<Entry> yValue_zero, yValue_one, yValue_two, yValue_three;
    private ArrayList<String> xValue;

    private ArrayList<LineDataSet> dataSets, dataSets_one;

    private BroadcastReceiver br;
    private int i = 0;
    private int yValue = 0;
    private int yValueTwo = 0;
    private int yValueThree = 0;
    private int yValueFour = 0;
    private int count = 0;

    private ThreadPlayAgain t ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logdetail_chart, null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chratOne = view.findViewById(R.id.linechart_logdetail_one);
        chartTwo = view.findViewById(R.id.linechart_logdetail_two);
        yValue_zero = new ArrayList<>();
        yValue_one = new ArrayList<>();
        yValue_two = new ArrayList<>();
        yValue_three = new ArrayList<>();
        xValue = new ArrayList<>();
        dataSets = new ArrayList<>();
        dataSets_one = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //一些图表的初始化
        initSettingChart();

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                i = intent.getIntExtra("count", 0);
                yValue = intent.getIntExtra("yValueOne", 0);
                yValueTwo = intent.getIntExtra("yValueTwo", 0);
                yValueThree = intent.getIntExtra("yValueThree", 0);
                yValueFour = intent.getIntExtra("yValueFour", 0);

                xValue.add(xValue.size() + "");


                chratOne.setVisibleXRangeMaximum(10);
                chartTwo.setVisibleXRangeMaximum(10);
                if (i > 10) {
                    chratOne.moveViewToX(i - 10);
                    chartTwo.moveViewToX(i - 10);
                }

                yValue_zero.add(new Entry(yValue, yValue_zero.size()));
                yValue_one.add(new Entry(yValueTwo , yValue_one.size()));
                yValue_two.add(new Entry(yValueThree , yValue_two.size()));
                yValue_three.add(new Entry(yValueFour , yValue_three.size()));


                mDataSet_zero.notifyDataSetChanged();
                mDataSet_one.notifyDataSetChanged();
                mDataSet_two.notifyDataSetChanged();
                mDataSet_three.notifyDataSetChanged();

                mData.notifyDataChanged();
                mData_one.notifyDataChanged();

                chratOne.notifyDataSetChanged();
                chratOne.invalidate();
                chartTwo.notifyDataSetChanged();
                chartTwo.invalidate();



            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("chartplayagain");
        getActivity().registerReceiver(br, intentFilter);

        //这个是存的   实际上这个要注释掉的  现在是为了方便打Log
//        final ArrayList<Integer> dataOne = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListOne").getDatas();
//        final ArrayList<Integer> dataTwo = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListTwo").getDatas();
//        final ArrayList<Integer> dataThree = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListThree").getDatas();
//        final ArrayList<Integer> dataFour = (ArrayList<Integer>) FileLTool.getOutInstance().getQueryKey("testListFour").getDatas();




        //这个是回放的线程
        t = new ThreadPlayAgain();
        t.setContext(getActivity());
        t.start();




    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(br);
        t.setExit(true);
    }

    private void initSettingChart() {

        mDataSet_zero = new LineDataSet(yValue_zero, "PIDzeroTitle");
        mDataSet_one = new LineDataSet(yValue_one, "PIDoneTitle");
        mDataSet_two = new LineDataSet(yValue_two, "PIDtwoTitle");
        mDataSet_three = new LineDataSet(yValue_three, "PIDthreeTitle");
        //设置下方描述的小方块的颜色   设置透明则线也消失消失
        mDataSet_zero.setColor(getResources().getColor(R.color.colorDisConnect));
        mDataSet_one.setColor(getResources().getColor(R.color.colorHUDtextColor));
        mDataSet_two.setColor(getResources().getColor(R.color.colorPrimary));
        mDataSet_three.setColor(getResources().getColor(R.color.colorDashboardsPointer));
        //设置线上面没有圆球
        mDataSet_zero.setDrawCircles(false);
        mDataSet_one.setDrawCircles(false);
        mDataSet_two.setDrawCircles(false);
        mDataSet_three.setDrawCircles(false);
        //设置数据依赖的Y轴
        mDataSet_zero.setAxisDependency(YAxis.AxisDependency.LEFT);
        mDataSet_one.setAxisDependency(YAxis.AxisDependency.RIGHT);
        mDataSet_two.setAxisDependency(YAxis.AxisDependency.LEFT);
        mDataSet_three.setAxisDependency(YAxis.AxisDependency.RIGHT);
        //设置数据颜色
        mDataSet_zero.setCircleColor(getResources().getColor(R.color.colorDisConnect));
        mDataSet_one.setCircleColor(getResources().getColor(R.color.colorHUDtextColor));
        mDataSet_two.setCircleColor(getResources().getColor(R.color.colorPrimary));
        mDataSet_three.setCircleColor(getResources().getColor(R.color.colorDashboardsPointer));
        //设置数据线的宽度
        mDataSet_zero.setLineWidth(2f);
        mDataSet_one.setLineWidth(2f);
        mDataSet_two.setLineWidth(2f);
        mDataSet_three.setLineWidth(2f);
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_1").getIsTure()) {
            mDataSet_zero.setDrawCubic(true);
        } else {
            mDataSet_zero.setDrawCubic(false);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_2").getIsTure()) {
            mDataSet_one.setDrawCubic(true);
        } else {
            mDataSet_one.setDrawCubic(false);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_3").getIsTure()) {
            mDataSet_two.setDrawCubic(true);
        } else {
            mDataSet_two.setDrawCubic(false);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_4").getIsTure()) {
            mDataSet_three.setDrawCubic(true);
        } else {
            mDataSet_three.setDrawCubic(false);
        }
        //这个是添加多组数据需要的
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_1").getIsTure()) {
            dataSets.add(mDataSet_zero);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_2").getIsTure()) {
            dataSets.add(mDataSet_one);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_3").getIsTure()) {
            dataSets_one.add(mDataSet_two);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_4").getIsTure()) {
            dataSets_one.add(mDataSet_three);
        }
        mData = new LineData(xValue, dataSets);
        mData_one = new LineData(xValue, dataSets_one);
        //设置不展示数字
        mData.setDrawValues(false);
        mData_one.setDrawValues(false);

//--------------------------------------------------------------------------------------------------------
        //这里加了另一个图表要新增个数据
        chratOne.setData(mData);
        chartTwo.setData(mData_one);

        chratOne.setDescription("");
        chartTwo.setDescription("");

        //背景色
        chratOne.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
        chratOne.setGridBackgroundColor(getResources().getColor(R.color.colorBlack));
        chartTwo.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
        chartTwo.setGridBackgroundColor(getResources().getColor(R.color.colorBlack));

        //启用/禁用绘制图表边框（chart周围的线）
        chratOne.setDrawBorders(false);
        chartTwo.setDrawBorders(false);

        //X轴
        XAxis xAxis = chratOne.getXAxis();
        XAxis xAxis_bottom = chartTwo.getXAxis();
        //轴出现位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis_bottom.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Xz轴文字颜色
        xAxis.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
        xAxis_bottom.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
        //避免X轴第一个和最后一个消失
        xAxis.setAvoidFirstLastClipping(true);
        xAxis_bottom.setAvoidFirstLastClipping(true);
        //设置为false则X轴刻度消失
        xAxis.setEnabled(true);
        xAxis_bottom.setEnabled(true);
        //设置X轴出现刻度的间距
        xAxis.setSpaceBetweenLabels(1);
        xAxis_bottom.setSpaceBetweenLabels(1);

        //Y轴
        YAxis left_yAxis = chratOne.getAxisLeft();
        YAxis right_yAxis = chratOne.getAxisRight();
        YAxis left_yAxis_bottom = chartTwo.getAxisLeft();
        YAxis right_yAxis_bottom = chartTwo.getAxisRight();
        //让系统自己测量最大值最小值
        left_yAxis.resetAxisMaxValue();
        left_yAxis.resetAxisMinValue();
        right_yAxis.resetAxisMaxValue();
        right_yAxis.resetAxisMinValue();
        left_yAxis_bottom.resetAxisMaxValue();
        left_yAxis_bottom.resetAxisMinValue();
        right_yAxis_bottom.resetAxisMaxValue();
        right_yAxis_bottom.resetAxisMinValue();
        //禁止数据反转
        left_yAxis.setInverted(false);
        right_yAxis.setInverted(false);
        left_yAxis_bottom.setInverted(false);
        right_yAxis_bottom.setInverted(false);
        //设置刻度在Y轴外面
        left_yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        right_yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        left_yAxis_bottom.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        right_yAxis_bottom.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置Y轴文字颜色
        left_yAxis.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
        right_yAxis.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
        left_yAxis_bottom.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
        right_yAxis_bottom.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
        //设置左右Y轴的颜色
        left_yAxis.setAxisLineColor(getResources().getColor(R.color.colorDisConnect));
        left_yAxis.setAxisLineWidth(1f);
        right_yAxis.setAxisLineColor(getResources().getColor(R.color.colorHUDtextColor));
        right_yAxis.setAxisLineWidth(1f);
        left_yAxis_bottom.setAxisLineColor(getResources().getColor(R.color.colorPrimary));
        left_yAxis_bottom.setAxisLineWidth(1f);
        right_yAxis_bottom.setAxisLineColor(getResources().getColor(R.color.colorDashboardsPointer));
        right_yAxis_bottom.setAxisLineWidth(1f);


    }

}
