package com.example.administrator.obdcheckerforaytophix10.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;


public class MainPersionalFragment extends Fragment implements View.OnClickListener {

    private TextView tv_menu;
    private RelativeLayout re_year, re_make, re_model, re_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_persional, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_menu = view.findViewById(R.id.tv_personal_menu);
        tv_menu.setOnClickListener(this);
        re_year = view.findViewById(R.id.re_personal_year);
        re_year.setOnClickListener(this);
        re_make = view.findViewById(R.id.re_personal_make);
        re_make.setOnClickListener(this);
        re_model = view.findViewById(R.id.re_personal_model);
        re_model.setOnClickListener(this);
        re_type = view.findViewById(R.id.re_personal_type);
        re_type.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personal_menu:
                //上方的Menu
                final OBDPopDialog dialog = new OBDPopDialog(getActivity());
                View view_dialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_personal_menu, null);
                //红色删除车辆信息按钮
                TextView tv_delete = view_dialog.findViewById(R.id.tv_personal_menu_delete);
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        OBDPopDialog dia_delete = new OBDPopDialog(getActivity());
                        View view_delete = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_personal_menu_delete, null);


                        setWin(dia_delete, 0.138666f, 0.333848f);
                        dia_delete.setContentView(view_delete);
                        dia_delete.setCanceledOnTouchOutside(true);
                        dia_delete.show();
                    }
                });


                setWin(dialog, 0.026666f, 0.323647f);
                dialog.setContentView(view_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            case R.id.re_personal_year:
                //Year  选择年份
                Intent intent = new Intent(getActivity(), MainFregmentReplaceActivity.class);
                intent.putExtra("intentKey", 2);
                startActivity(intent);
                break;
            case R.id.re_personal_make:
                Intent intent_make = new Intent(getActivity(), MainFregmentReplaceActivity.class);
                intent_make.putExtra("intentKey", 3);
                startActivity(intent_make);
                break;
            case R.id.re_personal_model:
                //Model
                Intent intent_model = new Intent(getActivity(), MainFregmentReplaceActivity.class);
                intent_model.putExtra("intentKey", 4);
                startActivity(intent_model);
                break;
            case R.id.re_personal_type:
                //Type
                Intent intent_type = new Intent(getActivity(), MainFregmentReplaceActivity.class);
                intent_type.putExtra("intentKey", 5);
                startActivity(intent_type);
                break;
        }

    }

    private void setWin(OBDPopDialog dia, float maleft, float matop) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(getActivity()) * maleft);
        lp.y = (int) (ScreenUtils.getScreenHeight(getActivity()) * matop);
        win.setAttributes(lp);
    }

}
