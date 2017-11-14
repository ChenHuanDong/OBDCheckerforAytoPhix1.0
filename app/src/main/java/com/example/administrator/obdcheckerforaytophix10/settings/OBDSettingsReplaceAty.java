package com.example.administrator.obdcheckerforaytophix10.settings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.OBDHUDSettingFragment;
import com.example.administrator.obdcheckerforaytophix10.settings.preferences.OBDSettingsCommunicaFragment;
import com.example.administrator.obdcheckerforaytophix10.settings.preferences.OBDSettingsGeneralFragment;
import com.example.administrator.obdcheckerforaytophix10.settings.preferences.OBDSettingsUnitsFragment;

public class OBDSettingsReplaceAty extends AppCompatActivity {

    private Fragment current_fragment , settings_communication , settings_units , settings_general;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_fregment_replaceother);

        Intent intent = getIntent();
        int i = intent.getIntExtra("intentKey", 0);
        if (i == 0) {
        } else if (i == 1) {
            settings_communication = new OBDSettingsCommunicaFragment();
            startFragmentAdd(settings_communication);
        }else if (i == 2){
            settings_units = new OBDSettingsUnitsFragment();
            startFragmentAdd(settings_units);
        }else if (i == 3){
            settings_general = new OBDSettingsGeneralFragment();
            startFragmentAdd(settings_general);
        }


    }


    private void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.frame_replace, fragment).commit();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment).add(R.id.frame_replace, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment).commit();
            }
            current_fragment = fragment;

        }
    }
}
