package com.example.administrator.obdcheckerforaytophix10.settings.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/10/12.
 */

public class OBDSettingsFirmwareUpdatesFragment extends Fragment implements View.OnClickListener {

    private ImageView iv_finish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_firmwareupdates , null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_finish = view.findViewById(R.id.iv_settings_firmware_finish);
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_settings_firmware_finish:
                getActivity().finish();
                break;
        }
    }
}
