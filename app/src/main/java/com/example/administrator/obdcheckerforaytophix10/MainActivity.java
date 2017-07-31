package com.example.administrator.obdcheckerforaytophix10;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.administrator.obdcheckerforaytophix10.main.MainOBDFragment;
import com.example.administrator.obdcheckerforaytophix10.main.MainPersionalFragment;
import com.example.administrator.obdcheckerforaytophix10.main.MainSpecialFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mRadioButton_obd, mRadioButton_special, mRadioButton_persional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏表图兰
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {
        mRadioButton_obd = (RadioButton) findViewById(R.id.btn_main_obd);
        mRadioButton_special = (RadioButton) findViewById(R.id.btn_main_special);
        mRadioButton_persional = (RadioButton) findViewById(R.id.btn_main_persional);
        mRadioButton_obd.setOnClickListener(this);
        mRadioButton_special.setOnClickListener(this);
        mRadioButton_persional.setOnClickListener(this);
        //设置第一个OBD按钮已经被点击
        mRadioButton_obd.performClick();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_obd:
                change_fragment(new MainOBDFragment());
                break;
            case R.id.btn_main_special:
                change_fragment(new MainSpecialFragment());
                break;
            case R.id.btn_main_persional:
                change_fragment(new MainPersionalFragment());
                break;
        }
    }
    //替换fragment方法
    public void change_fragment (Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.frame_main_replace , fragment);
        transaction.commit();
    }




}
