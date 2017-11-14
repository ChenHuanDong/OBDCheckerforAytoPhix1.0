package com.example.administrator.obdcheckerforaytophix10.settings.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

/**
 * Created by CHD on 2017/10/11.
 */

public class OBDSettingsGeneralFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_general, ll_currency;
    private RelativeLayout re_general;
    private ImageView iv_general_finish , iv_currency_finish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_general, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_general = view.findViewById(R.id.ll_settings_general);
        ll_currency = view.findViewById(R.id.ll_settings_currency);
        re_general = view.findViewById(R.id.re_setting_comm_device);
        re_general.setOnClickListener(this);
        iv_general_finish = view.findViewById(R.id.iv_settings_general_finish);
        iv_general_finish.setOnClickListener(this);
        iv_currency_finish = view.findViewById(R.id.iv_settings_currency_finish);
        iv_currency_finish.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_setting_comm_device:
                ll_general.setVisibility(View.GONE);
                ll_currency.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_settings_general_finish:
                getActivity().finish();
                break;
            case R.id.iv_settings_currency_finish:
                ll_general.setVisibility(View.VISIBLE);
                ll_currency.setVisibility(View.GONE);
                break;
        }
    }
}
