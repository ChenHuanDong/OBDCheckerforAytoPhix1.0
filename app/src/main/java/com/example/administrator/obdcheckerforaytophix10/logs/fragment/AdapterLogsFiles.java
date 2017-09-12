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

public class AdapterLogsFiles extends BaseAdapter {

    private ArrayList<BeanLogsFile> data;
    private Context mContext;

    public AdapterLogsFiles(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanLogsFile> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_logs_files , viewGroup , false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(data.get(i).getTitle());
        holder.tv_pid.setText(data.get(i).getPid());
        return view;
    }

    class ViewHolder {
        private TextView tv_title , tv_pid;
        public ViewHolder(View view) {
            tv_title = view.findViewById(R.id.tv_logs_item_file_title);
            tv_pid = view.findViewById(R.id.tv_logs_item_file_pid);
        }
    }

}
