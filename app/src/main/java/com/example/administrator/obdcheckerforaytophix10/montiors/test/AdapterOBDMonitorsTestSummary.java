package com.example.administrator.obdcheckerforaytophix10.montiors.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/14.
 */

public class AdapterOBDMonitorsTestSummary extends BaseAdapter {

    private ArrayList<BeanOBDMonitorsTestSummary> data;
    private Context mContext;

    public AdapterOBDMonitorsTestSummary(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanOBDMonitorsTestSummary> data) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_monitors_test_summary , viewGroup , false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(data.get(i).getContent());
        if (data.get(i).isRed()){
            holder.iv.setImageResource(R.drawable.monitors_close_red);
        }else {
            holder.iv.setImageResource(R.drawable.guzhang_y);
        }
        return view;
    }

    class ViewHolder {
        private TextView tv;
        private ImageView iv;
        public ViewHolder(View view) {
            tv = view.findViewById(R.id.tv_item_monitors_summary);
            iv = view.findViewById(R.id.iv_item_monitors_summary);
        }
    }

}
