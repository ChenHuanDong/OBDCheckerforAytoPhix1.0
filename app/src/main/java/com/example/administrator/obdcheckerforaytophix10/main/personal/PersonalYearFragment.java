package com.example.administrator.obdcheckerforaytophix10.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.MyListViewLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHD on 2017/9/27.
 */

public class PersonalYearFragment extends Fragment implements View.OnClickListener {

    private ListView lv;
    private AdapterPersonalYear mAdapter;
    private ArrayList<BeanPersonalYear> data;
    //左上角和右上角
    private ImageView iv_finish;
    private TextView tv_cancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_personal_year, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.mylv_personal_year);
        mAdapter = new AdapterPersonalYear(getActivity());
        data = new ArrayList<>();
        iv_finish = view.findViewById(R.id.iv_personal_otherreturn);
        iv_finish.setOnClickListener(this);
        tv_cancel = view.findViewById(R.id.tv_personal_othercancel);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 1996; i < 2018; i++) {
            BeanPersonalYear b = new BeanPersonalYear();
            b.setYear(i + "").setIsShow(false);
            data.add(b);
        }
        mAdapter.setData(data);
        lv.setAdapter(mAdapter);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.head_personal_tear, null);
        lv.addHeaderView(view);
        //点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    for (int i1 = 0; i1 < data.size(); i1++) {
                        data.get(i1).setIsShow(false);
                    }
                    data.get(i - 1).setIsShow(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //左上角结束按钮
            case R.id.iv_personal_otherreturn:
                getActivity().finish();
                break;
            case R.id.tv_personal_othercancel:
                getActivity().finish();
                break;
        }
    }
}
