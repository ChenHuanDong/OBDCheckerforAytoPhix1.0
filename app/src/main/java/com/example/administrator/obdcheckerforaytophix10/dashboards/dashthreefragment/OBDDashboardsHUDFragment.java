package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsHUDView;

import static com.example.administrator.obdcheckerforaytophix10.R.id.main_hud;

public class OBDDashboardsHUDFragment extends Fragment {

    private DashboardsHUDView hud;
    private RelativeLayout re;
    private ImageView icon;

    private boolean isRotation = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obddashboards_hud, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //横屏  这种设置 "自动旋转屏幕不会影响"
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        hud = view.findViewById(R.id.main_hud);
        re = view.findViewById(R.id.hud_out_re);
        icon = view.findViewById(R.id.hud_icon);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRotation) {
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(re, "rotationX", 0, 180)
                    );
                    set.setDuration(0).start();
                    isRotation = true;
                } else {
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(re, "rotationX", 180, 0)
                    );
                    set.setDuration(0).start();
                    isRotation = false;
                }
            }
        });


    }
}
