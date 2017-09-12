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

public class OBDLogsFilesFragment extends Fragment {

    private ListView lv;
    private AdapterLogsFiles mAdapter;
    private ArrayList<BeanLogsFile> data;
    //头布局
    private View view_head;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdlogs_files , null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_logs_files);
        mAdapter = new AdapterLogsFiles(getActivity());
        data = new ArrayList<>();
        view_head = LayoutInflater.from(getActivity()).inflate(R.layout.blank_three_five , null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < 4; i++) {
            BeanLogsFile bean = new BeanLogsFile();
            bean.setTitle("File" + (i+1)).setPid("Instat fuel economy");
            data.add(bean);
        }
        mAdapter.setData(data);
        lv.setAdapter(mAdapter);

        lv.addHeaderView(view_head);


    }
}
