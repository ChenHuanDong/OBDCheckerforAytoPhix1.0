package com.example.administrator.obdcheckerforaytophix10.main.obd;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/11/15.
 */

public class OBDDialogP extends Dialog {

    public OBDDialogP(@NonNull Context context) {
        super(context , R.style.kdialog);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyCompat();
    }

    private void applyCompat() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}
