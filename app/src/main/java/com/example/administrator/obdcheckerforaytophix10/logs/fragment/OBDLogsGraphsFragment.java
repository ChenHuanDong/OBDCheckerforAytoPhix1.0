package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/8.
 */

//回放的Fragment   LogsDetailChartFragment
//这个是刚开始展示的Fragment

//   现在猜测，如果添加数据的时候空指针的话  就是    mChart_zero mChart_one 空了，判断不为空的时候添加数据进表格

public class OBDLogsGraphsFragment extends Fragment {

    private LineChart mChart_zero, mChart_one;
    private RelativeLayout mRe;

    //下面两行五个要在onActivityCreat  里面初始化
    private LineData mData, mData_one;
    private LineDataSet mDataSet_zero, mDataSet_one, mDataSet_two, mDataSet_three;
    private ArrayList<Entry> yValue_zero, yValue_one, yValue_two, yValue_three;
    private ArrayList<String> xValue;

    private ArrayList<LineDataSet> dataSets, dataSets_one;

    private BroadcastReceiver br, brdata;
    private RelativeLayout.LayoutParams params_top;
    private RelativeLayout.LayoutParams params_bottom;
    private RelativeLayout.LayoutParams params , params_left ,params_right , params_horr;

    //计数
    private int i = 0;
    private int yValue = 0;
    private int yValueTwo = 0;
    private int yValueThree = 0;
    private int yValueFour = 0;
    private ArrayList<Integer> dataFileOne , dataFileTwo , dataFileThree , dataFileFour;



    //存数据的ArrayList
    private ArrayList<Integer> data;

    private int orientation;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdlogs_graphs, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yValue_zero = new ArrayList<>();
        yValue_one = new ArrayList<>();
        yValue_two = new ArrayList<>();
        yValue_three = new ArrayList<>();
        xValue = new ArrayList<>();
        mRe = view.findViewById(R.id.re_logs_graphs);
        dataSets = new ArrayList<>();
        dataSets_one = new ArrayList<>();
        dataFileOne = new ArrayList<>();
        dataFileTwo = new ArrayList<>();
        dataFileThree = new ArrayList<>();
        dataFileFour = new ArrayList<>();
        orientation = getResources().getConfiguration().orientation;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initView();

        //提出去 省的看着麻烦
        initSettingMyCharts();


        //这个是数据改变的广播
        brdata = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //这个是记录传回来的次数
                i = intent.getIntExtra("count", 0);
                data = intent.getIntegerArrayListExtra("key");

                yValue = data.get(0);
                yValueTwo = data.get(1);
                yValueThree = data.get(2);
                yValueFour = data.get(3);

                dataFileOne.add(yValue);
                dataFileTwo.add(yValueTwo);
                dataFileThree.add(yValueThree);
                dataFileFour.add(yValueFour);

                //判断如果是就储存到数据库里面
                if (intent.getBooleanExtra("finish" , false)){
                    //更改数据库   之前先存一个空的

                    FileLTool.getOutInstance().upDateColorByKey("testListOne" , dataFileOne);
                    FileLTool.getOutInstance().upDateColorByKey("testListTwo" , dataFileTwo);
                    FileLTool.getOutInstance().upDateColorByKey("testListThree" , dataFileThree);
                    FileLTool.getOutInstance().upDateColorByKey("testListFour" , dataFileFour);
                }


//                //可以在这里改变数据库  也可以在 OBDLogsActivity  里面新建广播来
                mChart_zero.setVisibleXRangeMaximum(10);
                mChart_one.setVisibleXRangeMaximum(10);
                if (i>10){
                    mChart_zero.moveViewToX(i - 10);
                    mChart_one.moveViewToX(i - 10);
                }

                yValue_zero.add(new Entry(yValue, yValue_zero.size()));
                yValue_one.add(new Entry(yValueTwo , yValue_one.size()));
                yValue_two.add(new Entry(yValueThree , yValue_two.size()));
                yValue_three.add(new Entry(yValueFour , yValue_three.size()));


                xValue.add(xValue.size() + "");

                mDataSet_zero.notifyDataSetChanged();
                mDataSet_one.notifyDataSetChanged();
                mDataSet_two.notifyDataSetChanged();
                mDataSet_three.notifyDataSetChanged();

                mData.notifyDataChanged();
                mData_one.notifyDataChanged();

                mChart_zero.notifyDataSetChanged();
                mChart_zero.invalidate();
                mChart_one.notifyDataSetChanged();
                mChart_one.invalidate();


            }
        };


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//-------------------------------这两个以后再设置-------------------------------------------------
//                第一个方法是界面可以显示的数目（按照原型的话是30（s））
//                第二个方法是从第几个X轴坐标开始显示   当X>30 时   没X+1  则参数 = 0 + 1
//                而且这个最好放在最下面。。。。
//                mChart_zero.setVisibleXRangeMaximum(10);
//                mChart_zero.moveViewToX(15);

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

                dataSets.clear();
                dataSets_one.clear();
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
                mRe.removeAllViews();

                mData = new LineData(xValue, dataSets);
                mData_one = new LineData(xValue, dataSets_one);

                mChart_zero.setData(mData);
                mChart_one.setData(mData_one);

                orientation = getResources().getConfiguration().orientation;
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_3").getIsTure() || DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_4").getIsTure()) {
                    if (orientation == 1){
                        mRe.addView(mChart_zero, params_top);
                        mRe.addView(mChart_one, params_bottom);
                    }else {
                        mRe.addView(mChart_zero, params_left);
                        mRe.addView(mChart_one, params_right);
                    }
                } else {
                    if (orientation == 1){
                        mRe.addView(mChart_zero, params);
                    }else {
                        mRe.addView(mChart_zero, params_horr);
                    }
                }


            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeLogsChart");
        getActivity().registerReceiver(br, intentFilter);

        IntentFilter intent_data = new IntentFilter();
        intent_data.addAction("bluetoothBT---logdata");
        getActivity().registerReceiver(brdata, intent_data);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mRe.removeAllViews();

        orientation = getResources().getConfiguration().orientation;
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_3").getIsTure() || DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_4").getIsTure()) {
            if (orientation == 1){
                mRe.addView(mChart_zero, params_top);
                mRe.addView(mChart_one, params_bottom);
            }else {
                mRe.addView(mChart_zero, params_left);
                mRe.addView(mChart_one, params_right);
            }
        } else {
            if (orientation == 1){
                mRe.addView(mChart_zero, params);
            }else {
                mRe.addView(mChart_zero, params_horr);
            }
        }

    }

    private void initView() {
        data = new ArrayList<>();
    }

    private void initSettingMyCharts() {
        mDataSet_zero = new LineDataSet(yValue_zero, "PIDzeroTitle");
        mDataSet_one = new LineDataSet(yValue_one, "PIDoneTitle");
        mDataSet_two = new LineDataSet(yValue_two, "PIDtwoTitle");
        mDataSet_three = new LineDataSet(yValue_three, "PIDthreeTitle");
        //设置下方描述的小方块的颜色   设置透明则线也消失消失
        mDataSet_zero.setColor(getResources().getColor(R.color.colorDisConnect));
        mDataSet_one.setColor(getResources().getColor(R.color.colorHUDtextColor));
        mDataSet_two.setColor(getResources().getColor(R.color.colorPrimary));
        mDataSet_three.setColor(getResources().getColor(R.color.colorDashboardsPointer));
        //设置线上的圆球的颜色
//        mDataSet_zero.setCircleColorHole(getResources().getColor(R.color.colorDisConnect));
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

        params_top = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 279f));
        params_bottom = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 279f));
        params_bottom.topMargin = (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 279f);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //横屏
        params_left = new RelativeLayout.LayoutParams((int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 333f),
                (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 280f));
        params_right = new RelativeLayout.LayoutParams((int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 333f),
                (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 280f));
        params_right.leftMargin = (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 333f);
        params_horr = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) ConversionUtil.myWantValue(ScreenUtils.getScreenWidth(getActivity()), 280f));

//----------------------------广播要变的    下面四组判断  *  2 ---------------------------
        //设置曲线变圆滑
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

        mChart_zero = new LineChart(getActivity());
        mChart_one = new LineChart(getActivity());


//----------------------------------广播改变的    下面if-----------------------------------------
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_3").getIsTure() || DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_4").getIsTure()) {
            if (orientation == 1){
                mRe.addView(mChart_zero, params_top);
                mRe.addView(mChart_one, params_bottom);
            }else {
                mRe.addView(mChart_zero, params_left);
                mRe.addView(mChart_one, params_right);
            }
        } else {
            if (orientation == 1){
                mRe.addView(mChart_zero, params);
            }else {
                mRe.addView(mChart_zero, params_horr);
            }
        }



        mChart_zero.setData(mData);
        mChart_one.setData(mData_one);


        //把右下角描述的文字设置为空
        mChart_zero.setDescription("");
        mChart_one.setDescription("");

        //背景色
        mChart_zero.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
        mChart_zero.setGridBackgroundColor(getResources().getColor(R.color.colorBlack));
        mChart_one.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
        mChart_one.setGridBackgroundColor(getResources().getColor(R.color.colorBlack));
        //启用/禁用绘制图表边框（chart周围的线）
        mChart_zero.setDrawBorders(false);
        mChart_one.setDrawBorders(false);
        //X轴
        XAxis xAxis = mChart_zero.getXAxis();
        XAxis xAxis_bottom = mChart_one.getXAxis();
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
        YAxis left_yAxis = mChart_zero.getAxisLeft();
        YAxis right_yAxis = mChart_zero.getAxisRight();
        YAxis left_yAxis_bottom = mChart_one.getAxisLeft();
        YAxis right_yAxis_bottom = mChart_one.getAxisRight();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(br);
        getActivity().unregisterReceiver(brdata);
    }



}
