package com.example.administrator.obdcheckerforaytophix10.montiors.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.MyListView;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/14.
 */

public class OBDMonitorsTestFragment extends Fragment {

    private MyListView lv , lv_result;
    private AdapterOBDMonitorsTestSummary mAdapter;
    private AdapterOBDMonitorsTestResults mAdapter_result;
    private ArrayList<BeanOBDMonitorsTestSummary> data;
    private ArrayList<BeanOBDMonitorsTestResult> data_result;
    //头布局
    private View view_head_summary  , view_head_result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdmonitors_test, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.mylv_monitors_test_summary);
        mAdapter = new AdapterOBDMonitorsTestSummary(getActivity());
        data = new ArrayList<>();
        view_head_summary = LayoutInflater.from(getActivity()).inflate(R.layout.lvhead_monitors_test_summary , null);
        lv_result = view.findViewById(R.id.mylv_monitors_test_result);
        data_result = new ArrayList<>();
        mAdapter_result = new AdapterOBDMonitorsTestResults(getActivity());
        view_head_result = LayoutInflater.from(getActivity()).inflate(R.layout.lvhead_monitors_test_result , null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //添加假数据
        BeanOBDMonitorsTestSummary bean = new BeanOBDMonitorsTestSummary();
        bean.setContent("Readiness Standard" + "\n" + "None").setRed(true);
        data.add(bean);
        BeanOBDMonitorsTestSummary bean2 = new BeanOBDMonitorsTestSummary();
        bean2.setContent("This vehcile is not ready for emissions testing").setRed(false);
        data.add(bean2);
        BeanOBDMonitorsTestSummary bean3 = new BeanOBDMonitorsTestSummary();
        bean3.setContent("MIL ON").setRed(false);
        data.add(bean3);
        BeanOBDMonitorsTestSummary bean4 = new BeanOBDMonitorsTestSummary();
        bean4.setContent("Number of Confirmed Codes:6").setRed(false);
        data.add(bean4);

        mAdapter.setData(data);
        lv.setAdapter(mAdapter);
        lv.addHeaderView(view_head_summary);


        BeanOBDMonitorsTestResult bean_1 = new BeanOBDMonitorsTestResult();
        bean_1.setContent("Readiness Standard" + "None").setAvailable(false).setComplete(true);
        data_result.add(bean_1);
        data_result.add(bean_1);
        data_result.add(bean_1);
        data_result.add(bean_1);
        data_result.add(bean_1);




        mAdapter_result.setData(data_result);
        lv_result.setAdapter(mAdapter_result);
        lv_result.addHeaderView(view_head_result);



    }
}
