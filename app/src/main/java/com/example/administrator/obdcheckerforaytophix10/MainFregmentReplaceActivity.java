package com.example.administrator.obdcheckerforaytophix10;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.administrator.obdcheckerforaytophix10.dashboards.FragmentBackListener;
import com.example.administrator.obdcheckerforaytophix10.dashboards.OBDHUDSettingFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsFileDetailFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalCalculationFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalFuelTypeFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalMakeFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalModelragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalSelectVehicleFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalTypeFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalYearFragment;
import com.example.administrator.obdcheckerforaytophix10.settings.other.OBDSettingsFirmwareUpdatesFragment;
import com.example.administrator.obdcheckerforaytophix10.settings.other.OBDSettingsInformationFragmnet;
import com.example.administrator.obdcheckerforaytophix10.settings.preferences.OBDSettingsCommunicaFragment;
import com.example.administrator.obdcheckerforaytophix10.settings.preferences.OBDSettingsPreferencesFragment;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

public class MainFregmentReplaceActivity extends AppCompatActivity {

    private Fragment current_fragment;

    private Fragment hud_setting, personal_year, personal_make, personal_model, personal_type, personal_fueltype, personal_canculation, personal_selectvehicle
            ,log_filedetail , settings_preferences , settings_communication ,
            settings_information , settings_firmwareupdates;

    private FragmentBackListener backListener;
    private boolean isInterception = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_fregment_replaceother);


        Intent intent = getIntent();
        int i = intent.getIntExtra("intentKey", 0);

        if (i == 0) {
        } else if (i == 1) {
            hud_setting = new OBDHUDSettingFragment();
            startFragmentAdd(hud_setting);
        } else if (i == 2) {
            personal_year = new PersonalYearFragment();
            startFragmentAdd(personal_year);
        } else if (i == 3) {
            personal_make = new PersonalMakeFragment();
            startFragmentAdd(personal_make);
        } else if (i == 4) {
            personal_model = new PersonalModelragment();
            startFragmentAdd(personal_model);
        } else if (i == 5) {
            personal_type = new PersonalTypeFragment();
            startFragmentAdd(personal_type);
        } else if (i == 6) {
            personal_fueltype = new PersonalFuelTypeFragment();
            startFragmentAdd(personal_fueltype);
        } else if (i == 7) {
            personal_canculation = new PersonalCalculationFragment();
            startFragmentAdd(personal_canculation);
        } else if (i == 8) {
            personal_selectvehicle = new PersonalSelectVehicleFragment();
            startFragmentAdd(personal_selectvehicle);
        } else if (i == 9) {
            log_filedetail = new OBDLogsFileDetailFragment();
            startFragmentAdd(log_filedetail);
        }else if (i == 10){
            settings_preferences = new OBDSettingsPreferencesFragment();
            startFragmentAdd(settings_preferences);
        }else if (i == 11){
            settings_communication = new OBDSettingsCommunicaFragment();
            startFragmentAdd(settings_communication);
        }else if (i == 12){
            settings_information = new OBDSettingsInformationFragmnet();
            startFragmentAdd(settings_information);
        }else if (i == 13){
            settings_firmwareupdates = new OBDSettingsFirmwareUpdatesFragment();
            startFragmentAdd(settings_firmwareupdates);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isInterception()) {

                if (backListener != null) {

                    backListener.onbackForward();
                    return false;
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public FragmentBackListener getBackListener() {
        return backListener;
    }

    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }

    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean interception) {
        isInterception = interception;
    }
}
