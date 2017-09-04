package com.example.administrator.obdcheckerforaytophix10.diagnostics.readinesstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/2.
 */

public class AdapterReadinessCommon extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> data;

    public AdapterReadinessCommon(Context context) {
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
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_diagnostic_readiness_common , viewGroup , false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(data.get(i));
        return view;
    }

    class ViewHolder {
        private TextView tv;

        public ViewHolder(View view) {
            tv = view.findViewById(R.id.tv_diagnostic_readiness_common);
        }
    }

}
