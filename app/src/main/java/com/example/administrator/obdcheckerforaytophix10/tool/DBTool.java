package com.example.administrator.obdcheckerforaytophix10.tool;

import com.example.administrator.obdcheckerforaytophix10.MainApplication;
import com.example.administrator.obdcheckerforaytophix10.OBDL;
import com.example.administrator.obdcheckerforaytophix10.OBDLDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by CHD on 2017/8/30.
 */
//单例可操作的类
public class DBTool {

    private static DBTool outInstance = new DBTool();

    private static OBDLDao sOBDLDao;

    //单例DBTool这个类
    public static DBTool getOutInstance() {
        //创建单例对象
        if (outInstance == null) {
            synchronized (DBTool.class) {
                if (outInstance == null) {
                    outInstance = new DBTool();
                }
            }
        }
        //初始化xxxDao
        sOBDLDao = MainApplication.getDaoSession().getOBDLDao();
        return outInstance;
    }


    //下面是对数据库进行操作的方法
    //增
    public void insertBean(OBDL obdl) {
        sOBDLDao.insert(obdl);
    }

    //删除所有
    public void deleteAll() {
        sOBDLDao.deleteAll();
    }

    //根据ID删除
    public void deleteById(Long id) {
        sOBDLDao.deleteByKey(id);
    }

    //根据Key删除
    public void deleteByKey(String key) {
        OBDL obdl = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key))
                .build().unique();
        if (obdl == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自DBTool");
        } else {
            sOBDLDao.delete(obdl);
        }
    }

    //查询所有
    public List<OBDL> queryAll() {
        List<OBDL> list = sOBDLDao.loadAll();
        return list;
    }

    //根据Key值获得obdl
    public OBDL getQueryKey(String key){
        OBDL obdl = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key))
                .build().unique();
        if (obdl == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自DBTool");
            return null;
        } else {
            return obdl;
        }
    }

    //下面是三个修改方法
    public void upDateColorByKey(String key , String color){
        OBDL obdl = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key))
                .build().unique();
        if (obdl == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自DBTool");
        } else {
            obdl.setColor(color);
            sOBDLDao.update(obdl);
        }
    }

    public void upDateValueByKey(String key , int value){
        OBDL obdl = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key))
                .build().unique();
        if (obdl == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自DBTool");
        } else {
            obdl.setValue(value);
            sOBDLDao.update(obdl);
        }
    }

    public void upDateFloatByKey(String key , float value){
        OBDL obdl = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key))
                .build().unique();
        if (obdl == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自DBTool");
        } else {
            obdl.setFloValue(value);
            sOBDLDao.update(obdl);
        }
    }

    //根据Key 查返回
    public void upDateIsTrueByKey(String key , boolean istrue){
        OBDL obdl = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key))
                .build().unique();
        if (obdl == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自DBTool");
        } else {
            obdl.setIsTure(istrue);
            sOBDLDao.update(obdl);
        }
    }

    //根据Key判断是否重复  在添加新数据前调用    （防止重复运行修改时有另个key 数据库不知道修改哪一个）
    public boolean isSave(String key) {
        QueryBuilder<OBDL> queryBuilder = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key));
        //查询name同名的个数
        Long size = queryBuilder.buildCount().count();
        return size > 0 ? true : false;
    }

    //根据Key判断   这个Key的数据有几条
    public long getByKeySize(String key){
        QueryBuilder<OBDL> queryBuilder = sOBDLDao.queryBuilder()
                .where(OBDLDao.Properties.Key.eq(key));
        //查询name同名的个数
        Long size = queryBuilder.buildCount().count();
        return size;
    }







}
