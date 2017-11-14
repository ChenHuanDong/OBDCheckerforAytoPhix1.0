package com.example.administrator.obdcheckerforaytophix10.settings.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.settings.OBDSettingsReplaceAty;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

/**
 * Created by CHD on 2017/10/10.
 */

public class OBDSettingsPreferencesFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout re_communic , re_units , re_general;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settinfs_perferences,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        re_communic = view.findViewById(R.id.re_settings_communic);
        re_communic.setOnClickListener(this);
        re_units = view.findViewById(R.id.re_settings_units);
        re_units.setOnClickListener(this);
        re_general = view.findViewById(R.id.re_settings_general);
        re_general.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_settings_communic:
                Intent intent = new Intent(getActivity() , OBDSettingsReplaceAty.class);
                intent.putExtra("intentKey" , 1);
                getActivity().startActivity(intent);
                break;
            case R.id.re_settings_units:
                Intent intent_units = new Intent(getActivity() , OBDSettingsReplaceAty.class);
                intent_units.putExtra("intentKey" , 2);
                getActivity().startActivity(intent_units);
                break;
            case R.id.re_settings_general:
                Intent intent_general = new Intent(getActivity() , OBDSettingsReplaceAty.class);
                intent_general.putExtra("intentKey" , 3);
                getActivity().startActivity(intent_general);
                break;
        }
    }
}
