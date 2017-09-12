package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/8.
 */

public class OBDLogsTripsFragment extends Fragment {

    private ListView lv;
    private ArrayList<BeanLogsTrips> data;
    private AdapterLogsTrips mAdapter;
    //LV 尾布局
    private View view_foot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdlogs_trips , null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_logs_trips);
        mAdapter = new AdapterLogsTrips(getActivity());
        data = new ArrayList<>();
        view_foot= LayoutInflater.from(getActivity()).inflate(R.layout.blank_three_five , null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < 3; i++) {
            BeanLogsTrips bean = new BeanLogsTrips();
            bean.setTitle("Trip" + (i+1)).setDistance("0.0miles").setFuel("0.00gal").setFuel_economy("0.00MPG");
            data.add(bean);
        }
        mAdapter.setData(data);
        lv.setAdapter(mAdapter);
        lv.addFooterView(view_foot);

    }
}
