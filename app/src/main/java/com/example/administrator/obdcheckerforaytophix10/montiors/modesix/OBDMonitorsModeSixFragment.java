package com.example.administrator.obdcheckerforaytophix10.montiors.modesix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.montiors.otwo.AdapterMonitorsOTwo;
import com.example.administrator.obdcheckerforaytophix10.montiors.otwo.BeanMonitorsOTwo;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/14.
 */

public class OBDMonitorsModeSixFragment extends Fragment {

    private ListView lv;
    private AdapterMonitorsOTwo mAdapter;
    private ArrayList<BeanMonitorsOTwo> data;
    private View view_head;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdmonitors_modesix, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new ArrayList<>();
        mAdapter = new AdapterMonitorsOTwo(getActivity());
        lv = view.findViewById(R.id.lv_monitors_modesix);
        view_head = LayoutInflater.from(getActivity()).inflate(R.layout.lvhead_monitors_test_summary , null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BeanMonitorsOTwo bean = new BeanMonitorsOTwo();
        bean.setContent("Mode 06").setMin(0f).setMinn(0f).setMax(0f).setUnits("V");
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);

        mAdapter.setData(data);
        lv.setAdapter(mAdapter);

        lv.addHeaderView(view_head);


    }
}
