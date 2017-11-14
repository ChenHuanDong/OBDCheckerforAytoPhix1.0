package com.example.administrator.obdcheckerforaytophix10;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHD on 2017/9/21.
 */

public class IntegerConverter implements PropertyConverter<List<Integer>, String> {

    private final Gson mGson;

    public IntegerConverter() {
        mGson = new Gson();
    }

    @Override  
    public List<Integer> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> list = mGson.fromJson(databaseValue , type);
        return list;
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        String dbString = mGson.toJson(entityProperty);
        return dbString;
    }

}
