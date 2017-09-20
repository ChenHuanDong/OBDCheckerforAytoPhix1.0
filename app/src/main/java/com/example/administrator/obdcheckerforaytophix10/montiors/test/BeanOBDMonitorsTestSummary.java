package com.example.administrator.obdcheckerforaytophix10.montiors.test;

/**
 * Created by CHD on 2017/9/14.
 */

public class BeanOBDMonitorsTestSummary {

    private String content ;
    private boolean isRed;



    public String getContent() {
        return content;
    }

    public BeanOBDMonitorsTestSummary setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isRed() {
        return isRed;
    }

    public BeanOBDMonitorsTestSummary setRed(boolean red) {
        isRed = red;
        return this;
    }
}
