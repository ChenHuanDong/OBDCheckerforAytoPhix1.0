package com.example.administrator.obdcheckerforaytophix10.diagnostics.historycodes;

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
 * Created by CHD on 2017/9/2.
 */

public class AdapterDiagnosticHistoryCode extends BaseAdapter {

    private ArrayList<BeanDiagnosticHistoryCodes> data;
    private Context mContext;

    public AdapterDiagnosticHistoryCode(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BeanDiagnosticHistoryCodes> data) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        LogUtil.fussenLog().d(data.get(position).getType()+"");
        if (data.get(position).getType() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderOne holderOne = null;
        ViewHolderTwo holderTwo = null;
        if (view == null) {
            switch (getItemViewType(i)) {
                case 0:
                    view = LayoutInflater.from(mContext).inflate(R.layout.item_diagnostic_history_type_one, viewGroup, false);
                    holderOne = new ViewHolderOne(view);
                    view.setTag(holderOne);
                    break;
                case 1:
                    view = LayoutInflater.from(mContext).inflate(R.layout.item_diagnostic_history_type_two, viewGroup, false);
                    holderTwo = new ViewHolderTwo(view);
                    view.setTag(holderTwo);
                    break;
            }
        } else {
            switch (getItemViewType(i)) {
                case 0:
                    holderOne = (ViewHolderOne) view.getTag();
                    break;
                case 1:
                    holderTwo = (ViewHolderTwo) view.getTag();
                    break;
            }
        }
        switch (getItemViewType(i)) {
            case 0:
                holderOne.tv_title.setText(data.get(i).getTitle());
                holderOne.tv_time.setText(data.get(i).getContent());
                break;
            case 1:
                holderTwo.tv_title.setText(data.get(i).getTitle());
                holderTwo.tv_content.setText(data.get(i).getContent());
                if (data.get(i).isRed()){
                    holderTwo.iv_red.setVisibility(View.VISIBLE);
                }else {
                    holderTwo.iv_yellow.setVisibility(View.VISIBLE);
                }
                break;
        }
        return view;
    }

    class ViewHolderOne {
        private TextView tv_title, tv_time;

        public ViewHolderOne(View view) {
            tv_title = view.findViewById(R.id.tv_diagnostic_history_one_title);
            tv_time = view.findViewById(R.id.tv_diagnostic_history_one_content);
        }
    }

    class ViewHolderTwo {
        private TextView tv_title, tv_content;
        private ImageView iv_red, iv_yellow;

        public ViewHolderTwo(View view) {
            tv_title = view.findViewById(R.id.tv_diagnostic_history_two_title);
            tv_content = view.findViewById(R.id.tv_diagnostic_history_two_content);
            iv_red = view.findViewById(R.id.iv_diagnostic_history_two_red);
            iv_yellow = view.findViewById(R.id.iv_diagnostic_history_two_yellow);
        }
    }


}
