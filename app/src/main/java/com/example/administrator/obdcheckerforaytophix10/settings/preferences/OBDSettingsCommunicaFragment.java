package com.example.administrator.obdcheckerforaytophix10.settings.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import ch.ielse.view.SwitchView;

/**
 * Created by CHD on 2017/10/10.
 */

//这个是从OBDSettingsReplaceAty跳转过来的
//剩下的用隐藏布局吧

//最后别忘了重写物理返回键！！  标记int  记录状态
public class OBDSettingsCommunicaFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_communication , ll_device , ll_protocol , ll_connection ;
    private ImageView iv_device_finish , iv_protocol_finish , iv_connection_finish , iv_finish;

    private RelativeLayout re_device , re_protocol , re_connection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_communication , null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_communication = view.findViewById(R.id.ll_settings_communication);
        ll_device = view.findViewById(R.id.ll_settings_device);
        ll_protocol = view.findViewById(R.id.ll_settings_protocol);
        ll_connection = view.findViewById(R.id.ll_settings_connection);

        re_device = view.findViewById(R.id.re_setting_comm_device);
        re_device.setOnClickListener(this);
        iv_device_finish = view.findViewById(R.id.iv_settings_device_finish);
        iv_device_finish.setOnClickListener(this);
        re_protocol = view.findViewById(R.id.re_setting_comm_protocol);
        re_protocol.setOnClickListener(this);
        iv_protocol_finish = view.findViewById(R.id.iv_settings_protocol_finish);
        iv_protocol_finish.setOnClickListener(this);
        iv_connection_finish = view.findViewById(R.id.iv_settings_connection_finish);
        iv_connection_finish.setOnClickListener(this);
        re_connection = view.findViewById(R.id.re_setting_comm_connection);
        re_connection.setOnClickListener(this);
        iv_finish = view.findViewById(R.id.iv_settings_communication_finish);
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_setting_comm_device:
                ll_communication.setVisibility(View.GONE);
                ll_device.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_settings_device_finish:
                ll_communication.setVisibility(View.VISIBLE);
                ll_device.setVisibility(View.GONE);
                break;
            case R.id.re_setting_comm_protocol:
                ll_communication.setVisibility(View.GONE);
                ll_protocol.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_settings_protocol_finish:
                ll_communication.setVisibility(View.VISIBLE);
                ll_protocol.setVisibility(View.GONE);
                break;
            case R.id.re_setting_comm_connection:
                ll_communication.setVisibility(View.GONE);
                ll_connection.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_settings_connection_finish:
                ll_communication.setVisibility(View.VISIBLE);
                ll_connection.setVisibility(View.GONE);
                break;
            case R.id.iv_settings_communication_finish:
                getActivity().finish();
                break;
        }
    }



}
