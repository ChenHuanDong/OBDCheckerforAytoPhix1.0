package com.example.administrator.obdcheckerforaytophix10.diagnostics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.historycodes.AdapterDiagnosticHistoryCode;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.historycodes.BeanDiagnosticHistoryCodes;
import com.example.administrator.obdcheckerforaytophix10.tool.MyListView;

import java.util.ArrayList;

public class OBDDiagnosticHistoricalActivity extends AppCompatActivity {

    private MyListView lv;
    private AdapterDiagnosticHistoryCode myAdapter;
    private ArrayList<BeanDiagnosticHistoryCodes> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obddiagnostic_historical);

        initView();

        initData(0, "FORD", "2017-7-25", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(0, "FORD", "2017-7-25", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(0, "FORD", "2017-7-25", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);


        myAdapter.setData(data);
        lv.setAdapter(myAdapter);


    }

    private void initData(int type, String title, String content, boolean isRed) {
        BeanDiagnosticHistoryCodes bean = new BeanDiagnosticHistoryCodes();
        bean.setType(type).setTitle(title).setContent(content).setRed(isRed);
        data.add(bean);
    }

    private void initView() {
        lv = (MyListView) findViewById(R.id.mylv_diagnostic_history);
        myAdapter = new AdapterDiagnosticHistoryCode(this);
        data = new ArrayList<>();
    }
}
