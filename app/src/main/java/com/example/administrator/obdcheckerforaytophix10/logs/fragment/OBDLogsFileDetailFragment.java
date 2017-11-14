package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/10/9.
 */

public class OBDLogsFileDetailFragment extends Fragment {

    private ViewPager vp;
    private ArrayList<Fragment> fs;
    private TabLayout tab;
    private AdapterLogsDetailTabLayout myAdapter;
    private String[] title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_obdlogs_filedetail , null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp = view.findViewById(R.id.vp_logdetail);
        tab = view.findViewById(R.id.tab_logsdetail);
        fs = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title = new String[]{getActivity().getResources().getString(R.string.chart),
                getActivity().getResources().getString(R.string.data)};

        fs.add(new LogsDetailChartFragment());
        fs.add(new LogsDetailDataFragment());

        myAdapter = new AdapterLogsDetailTabLayout(getActivity().getSupportFragmentManager() , fs , title );
        vp.setAdapter(myAdapter);

        tab.setupWithViewPager(vp);
        tab.setTabTextColors(getActivity().getResources().getColor(R.color.colorTextColorDemo),getActivity().getResources().getColor(R.color.colorTextColorDemo));
        tab.setSelectedTabIndicatorColor(getActivity().getResources().getColor(R.color.colorDashboardsPointer));




    }

}
