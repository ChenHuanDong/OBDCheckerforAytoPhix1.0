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

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/30.
 */

public class PersonalSelectVehicleFragment extends Fragment implements View.OnClickListener {

    private ListView lv;
    private AdapterPersonalYear mAdapter;
    private ArrayList<BeanPersonalYear> data;
    //左上  右上
    private TextView tv_cancel;
    private ImageView iv_finish;

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
        TextView tv = view.findViewById(R.id.tv_personal_othertitle);
        tv.setText(getResources().getString(R.string.selectvehicle));
        tv_cancel = view.findViewById(R.id.tv_personal_othercancel);
        tv_cancel.setOnClickListener(this);
        iv_finish = view.findViewById(R.id.iv_personal_otherreturn);
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initData("1A1JC5444R7252367", false);
        initData("1A1JC5444R7252367", false);
        initData("1A1JC5444R7252367", false);
        initData("1A1JC5444R7252367", false);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.head_blank, null);


        mAdapter.setData(data);
        lv.setAdapter(mAdapter);
        lv.addHeaderView(view);

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


    private void initData(String ac, boolean isShow) {
        BeanPersonalYear b = new BeanPersonalYear();
        b.setYear(ac).setIsShow(isShow);
        data.add(b);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personal_othercancel:
                getActivity().finish();
                break;
             case R.id.iv_personal_otherreturn:
                getActivity().finish();
                break;
        }
    }
}
