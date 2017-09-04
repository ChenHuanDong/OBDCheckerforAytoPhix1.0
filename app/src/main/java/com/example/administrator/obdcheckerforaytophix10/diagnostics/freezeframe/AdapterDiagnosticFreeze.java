package com.example.administrator.obdcheckerforaytophix10.diagnostics.freezeframe;

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

public class AdapterDiagnosticFreeze extends BaseAdapter {

    private ArrayList<BeanDiagnosticFreezeFrame> data;
    private Context mContext;

    public AdapterDiagnosticFreeze(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanDiagnosticFreezeFrame> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_diagnostic_freeze_frame , viewGroup , false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvUp.setText(data.get(i).getUpData());
        holder.tvBottom.setText(data.get(i).getBottomData());
        return view;
    }

    class ViewHolder {

        private TextView tvUp, tvBottom;

        public ViewHolder(View view) {
            tvUp = view.findViewById(R.id.tv_diagnostic_freeze_frame_itemup);
            tvBottom = view.findViewById(R.id.tv_diagnostic_freeze_frame_itemdown);
        }
    }

}
