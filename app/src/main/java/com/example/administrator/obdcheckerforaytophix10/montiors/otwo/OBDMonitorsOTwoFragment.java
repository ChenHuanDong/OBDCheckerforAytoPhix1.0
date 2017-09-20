package com.example.administrator.obdcheckerforaytophix10.montiors.otwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/14.
 */

public class OBDMonitorsOTwoFragment extends Fragment {

    private ListView lv;
    private AdapterMonitorsOTwo mAdapter;
    private ArrayList<BeanMonitorsOTwo> data;
    private View view_head;
    private TextView tv_head;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdmonitors_otwo , null);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_monitors_otwo);
        mAdapter = new AdapterMonitorsOTwo(getActivity());
        data = new ArrayList<>();
        view_head = LayoutInflater.from(getActivity()).inflate(R.layout.lvhead_monitors_test_summary,null);
        tv_head = view_head.findViewById(R.id.tv_lvhead_title);
        tv_head.setText(R.string.banksensor);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BeanMonitorsOTwo bean = new BeanMonitorsOTwo();
        bean.setContent("TID $01-Rich to lean sensor threshold voltage" + "\n" + "(constant)")
                .setMin(0f).setMinn(0.45f).setMax(1.275f).setUnits("v");
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);

        mAdapter.setData(data);
        lv.setAdapter(mAdapter);
        lv.addHeaderView(view_head);

    }
}
