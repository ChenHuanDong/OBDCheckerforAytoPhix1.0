package com.example.administrator.obdcheckerforaytophix10.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainOBDFragment extends Fragment implements View.OnClickListener {

    private TextView tv_title;
    private Button btn_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_obd, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View view) {
        tv_title = view.findViewById(R.id.obd_tv_title_connect);
        btn_title = view.findViewById(R.id.obd_btn_title_connect);
        tv_title.setOnClickListener(this);
        btn_title.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.obd_tv_title_connect:
                LogUtil.fussenLog().d("点击了tv");
                break;
            case R.id.obd_btn_title_connect:
                LogUtil.fussenLog().d("点击btn");
                break;
        }
    }
}
