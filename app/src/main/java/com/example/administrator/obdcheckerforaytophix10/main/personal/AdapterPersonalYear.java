package com.example.administrator.obdcheckerforaytophix10.main.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/27.
 */

public class AdapterPersonalYear extends BaseAdapter {

    private ArrayList<BeanPersonalYear> data;
    private Context mContext;

    public AdapterPersonalYear(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanPersonalYear> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_personal_year, viewGroup, false);
        TextView tv = view.findViewById(R.id.tv_item_personal_year);
        ImageView iv = view.findViewById(R.id.iv_item_personal_year);
        tv.setText(data.get(i).getYear());
        if (data.get(i).getisShow()) {
            iv.setVisibility(View.VISIBLE);
        }
        return view;
    }


}
