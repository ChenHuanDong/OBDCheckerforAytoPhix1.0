package com.example.administrator.obdcheckerforaytophix10;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.example.administrator.obdcheckerforaytophix10.main.MainOBDFragment;
import com.example.administrator.obdcheckerforaytophix10.main.MainPersionalFragment;
import com.example.administrator.obdcheckerforaytophix10.main.MainSpecialFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //底部三个RadioButton
    private RadioButton mRadioButton_obd, mRadioButton_special, mRadioButton_persional;
    //三个页面的Fragment
    private MainOBDFragment mOBDFragment;
    private MainSpecialFragment mSpecialFragment;
    private MainPersionalFragment mPersionalFragment;
    //判断现在是在哪一页
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //初始化
        initView();
        //设置第一个按钮不可点击
        mRadioButton_obd.setClickable(false);


    }

    private void initView() {
        mRadioButton_obd = (RadioButton) findViewById(R.id.btn_main_obd);
        mRadioButton_special = (RadioButton) findViewById(R.id.btn_main_special);
        mRadioButton_persional = (RadioButton) findViewById(R.id.btn_main_persional);
        mRadioButton_obd.setOnClickListener(this);
        mRadioButton_special.setOnClickListener(this);
        mRadioButton_persional.setOnClickListener(this);
        //Fragment初始化
        mOBDFragment = new MainOBDFragment();
        mSpecialFragment = new MainSpecialFragment();
        mPersionalFragment = new MainPersionalFragment();
        //初始页面设置为OBD页面
        change_fragment_noanim(mOBDFragment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_obd:
                //第一个不可点后两个可以点击
                mRadioButton_obd.setClickable(false);
                mRadioButton_special.setClickable(true);
                mRadioButton_persional.setClickable(true);

                change_fragment_obd(mOBDFragment, R.anim.slide_left_mid, R.anim.slide_mid_right);

                //设置页数在第一页
                page = 1;
                break;
            case R.id.btn_main_special:
                //第二个不可点后两个可以点击
                mRadioButton_obd.setClickable(true);
                mRadioButton_special.setClickable(false);
                mRadioButton_persional.setClickable(true);

                if (page == 1) {
                    change_fragment_obd(mSpecialFragment, R.anim.slide_right_mid, R.anim.slide_mid_left);
                }
                if (page == 3) {
                    change_fragment_obd(mSpecialFragment, R.anim.slide_left_mid, R.anim.slide_mid_right);
                }

                //设置页数在第二页
                page = 2;
                break;
            case R.id.btn_main_persional:
                //第三个不可点后两个可以点击
                mRadioButton_obd.setClickable(true);
                mRadioButton_special.setClickable(true);
                mRadioButton_persional.setClickable(false);

                change_fragment_obd(mPersionalFragment, R.anim.slide_right_mid, R.anim.slide_mid_left);

                //设置页数在第三页
                page = 3;
                break;
        }
    }

    //替换fragment方法 带动画
    private void change_fragment_obd(Fragment fragment, int anim_in, int anim_out) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //设置动画  必须要在add  remove  replace 之前调用
        transaction.setCustomAnimations(anim_in, anim_out);
        transaction.replace(R.id.frame_main_replace, fragment);
        //加入到BackStack
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //没有动画的切换fragment
    public void change_fragment_noanim(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_main_replace, fragment);
        transaction.commit();
    }


}
