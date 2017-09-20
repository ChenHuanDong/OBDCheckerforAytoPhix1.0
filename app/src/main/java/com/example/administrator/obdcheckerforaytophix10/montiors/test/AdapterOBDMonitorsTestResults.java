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

public class AdapterOBDMonitorsTestResults extends BaseAdapter {

    private ArrayList<BeanOBDMonitorsTestResult> data;
    private Context mContext;

    public AdapterOBDMonitorsTestResults(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanOBDMonitorsTestResult> data) {
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
        ViewHoler holer = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_monitors_test_result , viewGroup , false);
            holer = new ViewHoler(view);
            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        holer.tv.setText(data.get(i).getContent());
        if (data.get(i).isAvailable()){
            holer.iv_available.setImageResource(R.drawable.monitors_yes_gurren);
        }else {
            holer.iv_available.setImageResource(R.drawable.monitors_close_red);
        }
        if (data.get(i).isComplete()){
            holer.iv_complete.setImageResource(R.drawable.monitors_yes_gurren);
        }else {
            holer.iv_complete.setImageResource(R.drawable.monitors_close_red);
        }
        return view;
    }


    class ViewHoler{
        private TextView tv;
        private ImageView iv_available , iv_complete;
        public ViewHoler(View view) {
            tv = view.findViewById(R.id.tv_monitors_test_result);
            iv_available = view.findViewById(R.id.iv_monitors_test_available);
            iv_complete = view.findViewById(R.id.iv_monitors_test_complete);
        }
    }


}
