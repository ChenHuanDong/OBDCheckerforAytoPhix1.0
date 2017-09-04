package com.example.administrator.obdcheckerforaytophix10.diagnostics.historycodes;

/**
 * Created by CHD on 2017/9/2.
 */

public class BeanDiagnosticHistoryCodes {

    private int type;
    private String title , content;
    private boolean isRed ;

    public int getType() {
        return type;
    }

    public BeanDiagnosticHistoryCodes setType(int type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BeanDiagnosticHistoryCodes setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BeanDiagnosticHistoryCodes setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isRed() {
        return isRed;
    }

    public BeanDiagnosticHistoryCodes setRed(boolean red) {
        isRed = red;
        return this;
    }
}
