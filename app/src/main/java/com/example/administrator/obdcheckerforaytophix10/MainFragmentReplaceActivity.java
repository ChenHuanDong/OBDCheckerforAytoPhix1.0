package com.example.administrator.obdcheckerforaytophix10;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment.OBDDashboardsHUDFragment;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

//这个Aty  是管理点击跳转到所有的Fragment
//HUD的Fragment是1
//Style  是2

public class MainFragmentReplaceActivity extends AppCompatActivity {

    private FrameLayout replace;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_fragment_replace);

        Intent intent = getIntent();
        i = intent.getIntExtra("obdreplacefragment", 0);
        switch (i) {
            case 0:
                LogUtil.fussenLog().d("000");
                break;
            case 1:
                change_fragment_noanim(new OBDDashboardsHUDFragment());
                break;
            case 2:
                break;
        }


    }

    //没有动画的切换fragment
    public void change_fragment_noanim(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mian_replace, fragment);
        transaction.commit();
    }

}
