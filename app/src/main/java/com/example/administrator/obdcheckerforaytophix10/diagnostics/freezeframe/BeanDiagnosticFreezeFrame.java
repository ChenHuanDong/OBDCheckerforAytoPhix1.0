package com.example.administrator.obdcheckerforaytophix10.diagnostics.freezeframe;

/**
 * Created by CHD on 2017/9/2.
 */

public class BeanDiagnosticFreezeFrame {

    private String upData;
    private String BottomData;

    public String getUpData() {
        return upData;
    }

    public BeanDiagnosticFreezeFrame setUpData(String upData) {
        this.upData = upData;
        return this;
    }

    public String getBottomData() {
        return BottomData;
    }

    public BeanDiagnosticFreezeFrame setBottomData(String bottomData) {
        BottomData = bottomData;
        return this;
    }
}
