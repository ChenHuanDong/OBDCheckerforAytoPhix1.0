package com.example.administrator.obdcheckerforaytophix10;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by CHD on 2017/8/29.
 */
@Entity
public class OBDL {
    @Id
    private Long id;
    private String key;
    private String color;
    private int value;
    private boolean isTure;
    private float floValue;

    @Generated(hash = 67291497)
    public OBDL(Long id, String key, String color, int value, boolean isTure,
                float floValue) {
        this.id = id;
        this.key = key;
        this.color = color;
        this.value = value;
        this.isTure = isTure;
        this.floValue = floValue;
    }

    public OBDL(Long id, String key, String color) {
        this.id = id;
        this.key = key;
        this.color = color;
    }

    public OBDL(Long id, String key, int value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public OBDL(Long id, String key, boolean isTure) {
        this.id = id;
        this.key = key;
        this.isTure = isTure;
    }

    @Generated(hash = 1700707547)
    public OBDL() {
    }


    public Long getId() {
        return id;
    }

    public OBDL setId(Long id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public OBDL setKey(String key) {
        this.key = key;
        return this;
    }

    public String getColor() {
        return color;
    }

    public OBDL setColor(String color) {
        this.color = color;
        return this;
    }

    public int getValue() {
        return value;
    }

    public OBDL setValue(int value) {
        this.value = value;
        return this;
    }

    public boolean getIsTure() {
        return isTure;
    }

    public OBDL setTure(boolean ture) {
        isTure = ture;
        return this;
    }

    public void setIsTure(boolean isTure) {
        this.isTure = isTure;
    }

    public float getFloValue() {
        return this.floValue;
    }

    public void setFloValue(float floValue) {
        this.floValue = floValue;
    }


}
