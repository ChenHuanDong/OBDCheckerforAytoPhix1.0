package com.example.administrator.obdcheckerforaytophix10.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

public class OBDSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout re_preferences  , re_information , re_firmwareupdates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_obdsettings);

        initView();


    }

    private void initView() {
        re_preferences = (RelativeLayout) findViewById(R.id.re_settings_preferences);
        re_preferences.setOnClickListener(this);
        re_information = (RelativeLayout) findViewById(R.id.re_settings_information);
        re_information.setOnClickListener(this);
        re_firmwareupdates = (RelativeLayout) findViewById(R.id.re_settings_firmwareupdates);
        re_firmwareupdates.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_settings_preferences:
                Intent intent = new Intent(OBDSettingsActivity.this , MainFregmentReplaceActivity.class);
                intent.putExtra("intentKey" , 10);
                startActivity(intent);
                break;
            case R.id.re_settings_information:
                Intent intent_information = new Intent(OBDSettingsActivity.this , MainFregmentReplaceActivity.class);
                intent_information.putExtra("intentKey" , 12);
                startActivity(intent_information);
                break;
            case R.id.re_settings_firmwareupdates:
                Intent intent_firmware = new Intent(OBDSettingsActivity.this , MainFregmentReplaceActivity.class);
                intent_firmware.putExtra("intentKey" , 13);
                startActivity(intent_firmware);
                break;
        }
    }
}
