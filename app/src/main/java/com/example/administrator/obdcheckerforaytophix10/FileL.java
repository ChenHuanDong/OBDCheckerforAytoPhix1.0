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

    @Convert(columnType = String.class, converter = IntegerConverter.class)
    private List<Integer> datas;

    @Generated(hash = 1452823494)
    public FileL(Long id, @NotNull String key, List<Integer> datas) {
        this.id = id;
        this.key = key;
        this.datas = datas;
    }

    @Generated(hash = 577372799)
    public FileL() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Integer> getDatas() {
        return this.datas;
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
    }


}
