package com.example.administrator.obdcheckerforaytophix10.montiors.otwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/15.
 */

public class AdapterMonitorsOTwo extends BaseAdapter {

    private ArrayList<BeanMonitorsOTwo> data;
    private Context mContext;

    public AdapterMonitorsOTwo(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanMonitorsOTwo> data) {
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
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_monitors_otwo, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_content.setText(data.get(i).getContent());
        holder.tv_min.setText(data.get(i).getMin()+"");
        holder.tv_minn.setText(data.get(i).getMinn()+"");
        holder.tv_max.setText(data.get(i).getMax()+"");
        holder.tv_units.setText(data.get(i).getUnits()+"");
        return view;
    }

    class ViewHolder {
        private TextView tv_content, tv_min, tv_minn, tv_max, tv_units;

        public ViewHolder(View view) {
            tv_content = view.findViewById(R.id.tv_monitors_otwo_item_title);
            tv_min = view.findViewById(R.id.tv_monitors_otwo_item_min);
            tv_minn = view.findViewById(R.id.tv_monitors_otwo_item_minn);
            tv_max = view.findViewById(R.id.tv_monitors_otwo_item_max);
            tv_units = view.findViewById(R.id.tv_monitors_otwo_item_units);
        }
    }

}
