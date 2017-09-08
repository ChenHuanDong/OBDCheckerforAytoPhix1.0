package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/9/8.
 */

public class OBDLogsGraphsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obdlogs_graphs , null);
        return view;
    }
}
