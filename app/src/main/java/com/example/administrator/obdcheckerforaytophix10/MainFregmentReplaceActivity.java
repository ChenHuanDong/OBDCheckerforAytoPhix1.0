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
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalMakeFragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalModelragment;
import com.example.administrator.obdcheckerforaytophix10.main.personal.PersonalYearFragment;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

public class MainFregmentReplaceActivity extends AppCompatActivity {

    private Fragment current_fragment;

    //该新建Fragment了   照着左边的来  布局一样  id一样  就是  有一些String
    //和添加的数据不一样
    private Fragment hud_setting , personal_year , personal_make , personal_model
            , personal_type;

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

        hud_setting = new OBDHUDSettingFragment();
        personal_year = new PersonalYearFragment();
        personal_make = new PersonalMakeFragment();
        personal_model = new PersonalModelragment();


        Intent intent = getIntent();
        int i =  intent.getIntExtra("intentKey" , 0);
        if (i == 0){
        }else if (i == 1){
            startFragmentAdd(hud_setting);
        }else if(i == 2){
            startFragmentAdd(personal_year);
        }else if (i == 3){
            startFragmentAdd(personal_make);
        }else if (i == 4){
            startFragmentAdd(personal_model);
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
