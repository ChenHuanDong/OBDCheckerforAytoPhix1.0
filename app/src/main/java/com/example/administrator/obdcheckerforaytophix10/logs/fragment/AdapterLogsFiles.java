package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by CHD on 2017/9/12.
 */

public class AdapterLogsFiles extends BaseAdapter {

    private ArrayList<BeanLogsFile> data;
    private Context mContext;
    private ViewHolder holder;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_logs_files, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(data.get(i).getTitle());
        holder.tv_pid.setText(data.get(i).getPid());
        if (data.get(i).getShow()) {
            holder.iv_del.setVisibility(View.VISIBLE);
        } else {
            holder.iv_del.setVisibility(View.GONE);
        }

        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OBDPopDialog dialog = new OBDPopDialog(mContext);
                View view_rd = LayoutInflater.from(mContext).inflate(R.layout.dialog_display_remove_display, null);
                Button btn_ok = view_rd.findViewById(R.id.btn_display_remove_ok);
                Button btn_cancel = view_rd.findViewById(R.id.btn_display_remove_cancel);
                TextView tv_title = view_rd.findViewById(R.id.tv_delete_title);
                tv_title.setText(R.string.delete);
                TextView tv_content = view_rd.findViewById(R.id.tv_delete_content);
                tv_content.setText(R.string.delete_file_dia);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //以后这里还要写数据库的删除
                        data.remove(i);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });



                setPromptWin(dialog);
                dialog.setContentView(view_rd);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

            }
        });

        return view;
    }

    class ViewHolder {
        private TextView tv_title, tv_pid;
        private ImageView iv_del;

        public ViewHolder(View view) {
            tv_title = view.findViewById(R.id.tv_logs_item_file_title);
            tv_pid = view.findViewById(R.id.tv_logs_item_file_pid);
            iv_del = view.findViewById(R.id.iv_logs_item_file_del);
        }
    }

    public void setIvDelGone() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setShow(false);
        }
        notifyDataSetChanged();
    }

    public void setIvDelVisibily() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setShow(true);
        }
        notifyDataSetChanged();
    }

    private void setPromptWin(OBDPopDialog dia) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(mContext) * 0.141333);
        lp.y = (int) (ScreenUtils.getScreenHeight(mContext) * 0.293663);
        win.setAttributes(lp);
    }


}
