package com.example.administrator.obdcheckerforaytophix10.diagnostics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.troublecode.AdapterDiagnoticsTrouble;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.troublecode.BeanDiagnoticsTroubleCode;

import java.util.ArrayList;

public class OBDDiagnosticsActivity extends AppCompatActivity implements View.OnClickListener {

    //下面四个可切换的Re
    private RelativeLayout re_trouble_code, re_freeze_frame, re_readiness_test, re_report;
    private ImageView iv_trouble_code , iv_freeze_frame, iv_readiness_test, iv_report;
    private TextView tv_trouble_code , tv_freeze_frame, tv_readiness_test, tv_report;
    private LinearLayout ll_trouble_code , ll_freeze_frame , ll_readiness_test , ll_report;

    private ListView lv_trouble_code;
    private ArrayList<BeanDiagnoticsTroubleCode> data_trouble_code;
    private AdapterDiagnoticsTrouble myAdapterTroubleCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obddiagnostics);

        initView();
        re_trouble_code.performClick();
        BeanDiagnoticsTroubleCode bean = new BeanDiagnoticsTroubleCode();
        for (int i = 0; i < 50; i++) {
            bean.setTitle("P0103").setItem("02---"+(i+1)).setRed(true);
            data_trouble_code.add(bean);
        }
        myAdapterTroubleCode.setData(data_trouble_code);
        lv_trouble_code.setAdapter(myAdapterTroubleCode);



    }

    private void initView() {
        re_trouble_code = (RelativeLayout) findViewById(R.id.re_diagnostic_trouble_code);
        re_freeze_frame = (RelativeLayout) findViewById(R.id.re_diagnostic_freeze_frame);
        re_readiness_test = (RelativeLayout) findViewById(R.id.re_diagnostic_readiness_test);
        re_report = (RelativeLayout) findViewById(R.id.re_diagnostic_report);
        re_trouble_code.setOnClickListener(this);
        re_freeze_frame.setOnClickListener(this);
        re_readiness_test.setOnClickListener(this);
        re_report.setOnClickListener(this);
        iv_trouble_code = (ImageView) findViewById(R.id.iv_diagnostic_trouble_code);
        iv_freeze_frame = (ImageView) findViewById(R.id.iv_diagnostic_freeze_frame);
        iv_readiness_test = (ImageView) findViewById(R.id.iv_diagnostic_readiness_test);
        iv_report = (ImageView) findViewById(R.id.iv_diagnostic_report);
        tv_trouble_code = (TextView) findViewById(R.id.tv_diagnostic_trouble_code);
        tv_freeze_frame = (TextView) findViewById(R.id.tv_diagnostic_freeze_frame);
        tv_readiness_test = (TextView) findViewById(R.id.tv_diagnostic_readiness_test);
        tv_report = (TextView) findViewById(R.id.tv_diagnostic_report);
        ll_trouble_code = (LinearLayout) findViewById(R.id.ll_mid_trouble_code);
        ll_freeze_frame = (LinearLayout) findViewById(R.id.ll_mid_freeze_frame);
        ll_readiness_test = (LinearLayout) findViewById(R.id.ll_mid_readiness_rest);
        ll_report = (LinearLayout) findViewById(R.id.ll_mid_report);
        //Trouble  Code
        lv_trouble_code = (ListView) findViewById(R.id.lv_diagnostic_trouble_code);
        myAdapterTroubleCode = new AdapterDiagnoticsTrouble(this);
        data_trouble_code = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_diagnostic_trouble_code:
                ll_trouble_code.setVisibility(View.VISIBLE);
                ll_freeze_frame.setVisibility(View.GONE);
                ll_readiness_test.setVisibility(View.GONE);
                ll_report.setVisibility(View.GONE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_y);
                iv_freeze_frame.setImageResource(R.drawable.freeze_b);
                iv_readiness_test.setImageResource(R.drawable.readiness_b);
                iv_report.setImageResource(R.drawable.report_balck);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_report.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.re_diagnostic_freeze_frame:
                ll_trouble_code.setVisibility(View.GONE);
                ll_freeze_frame.setVisibility(View.VISIBLE);
                ll_readiness_test.setVisibility(View.GONE);
                ll_report.setVisibility(View.GONE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_b);
                iv_freeze_frame.setImageResource(R.drawable.freeze_y);
                iv_readiness_test.setImageResource(R.drawable.readiness_b);
                iv_report.setImageResource(R.drawable.report_balck);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_report.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.re_diagnostic_readiness_test:
                ll_trouble_code.setVisibility(View.GONE);
                ll_freeze_frame.setVisibility(View.GONE);
                ll_readiness_test.setVisibility(View.VISIBLE);
                ll_report.setVisibility(View.GONE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_b);
                iv_freeze_frame.setImageResource(R.drawable.freeze_b);
                iv_readiness_test.setImageResource(R.drawable.readiness_y);
                iv_report.setImageResource(R.drawable.report_balck);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                tv_report.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.re_diagnostic_report:
                ll_trouble_code.setVisibility(View.GONE);
                ll_freeze_frame.setVisibility(View.GONE);
                ll_readiness_test.setVisibility(View.GONE);
                ll_report.setVisibility(View.VISIBLE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_b);
                iv_freeze_frame.setImageResource(R.drawable.freeze_b);
                iv_readiness_test.setImageResource(R.drawable.readiness_b);
                iv_report.setImageResource(R.drawable.report_y);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_report.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                break;
        }
    }
}
