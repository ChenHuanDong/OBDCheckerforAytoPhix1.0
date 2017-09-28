package com.example.administrator.obdcheckerforaytophix10;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by CHD on 2017/9/21.
 */
@Entity
public class FileL {

    @Id
    private Long id;

    @NotNull
    private String key;
    private int value;
    private boolean isTure;

    @Convert(columnType = String.class, converter = IntegerConverter.class)
    private List<Integer> datas;

    @Generated(hash = 1740173592)
    public FileL(Long id, @NotNull String key, int value, boolean isTure,
            List<Integer> datas) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.isTure = isTure;
        this.datas = datas;
    }

    @Generated(hash = 577372799)
    public FileL() {
    }

    public Long getId() {
        return id;
    }

    public FileL setId(Long id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public FileL setKey(String key) {
        this.key = key;
        return this;
    }

    public int getValue() {
        return value;
    }

    public FileL setValue(int value) {
        this.value = value;
        return this;
    }

    public boolean getisTure() {
        return isTure;
    }

    public FileL setTure(boolean ture) {
        isTure = ture;
        return this;
    }

    public List<Integer> getDatas() {
        return datas;
    }

    public FileL setDatas(List<Integer> datas) {
        this.datas = datas;
        return this;
    }

    public boolean getIsTure() {
        return this.isTure;
    }

    public void setIsTure(boolean isTure) {
        this.isTure = isTure;
    }
}
