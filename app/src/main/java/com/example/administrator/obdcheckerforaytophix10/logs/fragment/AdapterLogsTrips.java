package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/12.
 */

public class AdapterLogsTrips extends BaseAdapter {

    private ArrayList<BeanLogsTrips> data;
    private Context mContext;

    public AdapterLogsTrips(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanLogsTrips> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_logs_trip , viewGroup , false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_distance.setText(data.get(i).getDistance());
        holder.tv_fuel.setText(data.get(i).getFuel());
        holder.tv_fueleconomy.setText(data.get(i).getFuel_economy());
        holder.tv_title.setText(data.get(i).getTitle());
        return view;
    }

    class ViewHolder {
        private TextView tv_distance , tv_fuel , tv_fueleconomy , tv_title;
        public ViewHolder(View view) {
            tv_distance = view.findViewById(R.id.tv_logs_trip_distance);
            tv_fuel = view.findViewById(R.id.tv_logs_trip_fuel);
            tv_fueleconomy = view.findViewById(R.id.tv_logs_trip_economy);
            tv_title = view.findViewById(R.id.tv_logs_trip_title);
        }
    }

}
