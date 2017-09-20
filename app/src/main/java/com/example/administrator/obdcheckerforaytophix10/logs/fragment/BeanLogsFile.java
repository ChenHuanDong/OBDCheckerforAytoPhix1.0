package com.example.administrator.obdcheckerforaytophix10.logs.fragment;

/**
 * Created by CHD on 2017/9/12.
 */

public class BeanLogsFile {
    private String title , pid ;
    private Boolean isShow = false ;

    public Boolean getShow() {
        return isShow;
    }

    public BeanLogsFile setShow(Boolean show) {
        isShow = show;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BeanLogsFile setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public BeanLogsFile setPid(String pid) {
        this.pid = pid;
        return this;
    }
}
