package com.example.administrator.obdcheckerforaytophix10.diagnostics.troublecode;

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
 * Created by CHD on 2017/8/30.
 */

public class AdapterDiagnoticsTrouble extends BaseAdapter {

    private ArrayList<BeanDiagnoticsTroubleCode> data;
    private Context mContext;

    public AdapterDiagnoticsTrouble(Context context) {
        mContext = context;
    }

    public AdapterDiagnoticsTrouble setData(ArrayList<BeanDiagnoticsTroubleCode> data) {
        this.data = data;
        notifyDataSetChanged();
        return this;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_diagnostic_trouble_code , viewGroup , false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(data.get(i).getTitle());
        holder.tv_item.setText(data.get(i).getItem());
        if (data.get(i).isRed()){
            holder.iv_red.setVisibility(View.VISIBLE);
            holder.iv_yellow.setVisibility(View.GONE);
        }else {
            holder.iv_yellow.setVisibility(View.VISIBLE);
            holder.iv_red.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        private TextView tv_title, tv_item;
        private ImageView iv_red, iv_yellow;

        public ViewHolder(View view) {
            tv_title = view.findViewById(R.id.tv_trouble_item_title);
            tv_item = view.findViewById(R.id.tv_trouble_item_item);
            iv_red = view.findViewById(R.id.iv_trouble_item_red);
            iv_yellow = view.findViewById(R.id.iv_trouble_item_yellow);
        }
    }

}
