package com.example.administrator.obdcheckerforaytophix10.montiors.test;

/**
 * Created by CHD on 2017/9/14.
 */

public class BeanOBDMonitorsTestResult {

    private String content;
    private boolean isAvailable , isComplete ;

    public String getContent() {
        return content;
    }

    public BeanOBDMonitorsTestResult setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public BeanOBDMonitorsTestResult setAvailable(boolean available) {
        isAvailable = available;
        return this;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public BeanOBDMonitorsTestResult setComplete(boolean complete) {
        isComplete = complete;
        return this;
    }
}
