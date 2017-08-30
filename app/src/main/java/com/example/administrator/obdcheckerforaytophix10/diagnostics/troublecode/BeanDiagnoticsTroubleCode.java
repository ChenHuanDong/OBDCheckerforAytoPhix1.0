package com.example.administrator.obdcheckerforaytophix10.diagnostics.troublecode;

/**
 * Created by CHD on 2017/8/30.
 */

public class BeanDiagnoticsTroubleCode {

    private String title;
    private String item;
    private boolean isRed;

    public BeanDiagnoticsTroubleCode(String title, String item, boolean isRed) {
        this.title = title;
        this.item = item;
        this.isRed = isRed;
    }

    public BeanDiagnoticsTroubleCode() {
    }

    public String getTitle() {
        return title;
    }

    public BeanDiagnoticsTroubleCode setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getItem() {
        return item;
    }

    public BeanDiagnoticsTroubleCode setItem(String item) {
        this.item = item;
        return this;
    }

    public boolean isRed() {
        return isRed;
    }

    public BeanDiagnoticsTroubleCode setRed(boolean red) {
        isRed = red;
        return this;
    }
}
