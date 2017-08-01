package com.example.administrator.obdcheckerforaytophix10.main.obd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/8/1.
 */

public class MainOBDPopLsAdapter extends BaseAdapter {

    private ArrayList<String> data;
    private Context mContext;

    public MainOBDPopLsAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<String> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_obd_pop_ls, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(data.get(i));
        return view;
    }


    class ViewHolder {
        private TextView tv;

        public ViewHolder(View view) {
            tv = view.findViewById(R.id.item_tv_obd_pop_device);
        }
    }


}
