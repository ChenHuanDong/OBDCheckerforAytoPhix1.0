package com.example.administrator.obdcheckerforaytophix10.tool;

import com.example.administrator.obdcheckerforaytophix10.FileL;
import com.example.administrator.obdcheckerforaytophix10.FileLDao;
import com.example.administrator.obdcheckerforaytophix10.MainApplication;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHD on 2017/9/21.
 */
//单例可操作的类
//存了HUD的颜色

//所有数据库的增  都在MainActivity  里面进行

public class FileLTool {

    private static FileLTool outInstance = new FileLTool();

    private static FileLDao sFileLDao;

    public static FileLTool getOutInstance() {
        //创建单例对象
        if (outInstance == null) {
            synchronized (FileLTool.class) {
                if (outInstance == null) {
                    outInstance = new FileLTool();
                }
            }
        }
        sFileLDao = MainApplication.getDaoSession().getFileLDao();
        return outInstance;
    }

    //下面是对数据库进行操作的方法
    //增
    public void insertBean(FileL fileL) {
        sFileLDao.insert(fileL);
    }

    //删除所有
    public void deleteAll() {
        sFileLDao.deleteAll();
    }

    //根据ID删除
    public void deleteById(Long id) {
        sFileLDao.deleteByKey(id);
    }

    //根据Key删除
    public void deleteByKey(String key) {
        FileL fileL = sFileLDao.queryBuilder()
                .where(FileLDao.Properties.Key.eq(key))
                .build().unique();
        if (fileL == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自FileLTool");
        } else {
            sFileLDao.delete(fileL);
        }
    }

    //根据Key值获得fileL
    public FileL getQueryKey(String key) {
        FileL fileL = sFileLDao.queryBuilder()
                .where(FileLDao.Properties.Key.eq(key))
                .build().unique();
        if (fileL == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自FileLTool");
            return null;
        } else {
            return fileL;
        }
    }

    //根据Key判断是否重复  在添加新数据前调用    （防止重复运行修改时有另个key 数据库不知道修改哪一个）
    public boolean isSave(String key) {
        QueryBuilder<FileL> queryBuilder = sFileLDao.queryBuilder()
                .where(FileLDao.Properties.Key.eq(key));
        Long size = queryBuilder.buildCount().count();
        return size > 0 ? true : false;
    }

    //根据Key  更改内容

    //改变  List<Integer>
    public void upDateColorByKey(String key, ArrayList<Integer> data) {
        FileL fileL = sFileLDao.queryBuilder()
                .where(FileLDao.Properties.Key.eq(key))
                .build().unique();
        if (fileL == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自FileLTool");
        } else {
            fileL.setDatas(data);
            sFileLDao.update(fileL);
        }
    }

    //改变  value
    public void upDateValueByKey(String key, int a) {
        FileL fileL = sFileLDao.queryBuilder()
                .where(FileLDao.Properties.Key.eq(key))
                .build().unique();
        if (fileL == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自FileLTool");
        } else {
            fileL.setValue(a);
            sFileLDao.update(fileL);
        }
    }

    //改变  boolean
    public void upDateIsTureByKey(String key, boolean a) {
        FileL fileL = sFileLDao.queryBuilder()
                .where(FileLDao.Properties.Key.eq(key))
                .build().unique();
        if (fileL == null) {
            LogUtil.fussenLog().d("Key值为" + key + "的数据是空的------来自FileLTool");
        } else {
            fileL.setIsTure(a);
            sFileLDao.update(fileL);
        }
    }


}
