package com.example.administrator.obdcheckerforaytophix10.main.obd;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/8/3.
 */
//这个是防止四周出现白边的Dialog
public class OBDPopDialog extends Dialog {

    public OBDPopDialog(@NonNull Context context) {
        super(context, R.style.ShareDialog);
    }

}
