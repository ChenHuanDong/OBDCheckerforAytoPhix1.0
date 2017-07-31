package com.example.administrator.obdcheckerforaytophix10.tool;

import android.util.Log;

import java.util.Hashtable;
import java.util.Objects;

/**
 * Created by CHD on 2017/7/31.
 */

public class LogUtil {

    private final static boolean logFlag = true ;

    public final static String tag = "[obdchecker]" ;
    private final static int logLevel = Log.VERBOSE ;
    private static Hashtable<String , LogUtil> sLoggerTable  = new Hashtable<String, LogUtil>();
    private String mClassName ;

    private static LogUtil fussenLog ;
    private static LogUtil gaorLog ;
    private static LogUtil chunLog ;

    private static final String FUSSEN = "@fussen@" ;
    private static final String CHUNY = "@chuny@" ;
    private static final String GAOR = "@gaor@" ;

    private LogUtil(String name){
        mClassName = name ;
    }

    private static LogUtil getLogger(String className){
        LogUtil classLogger = sLoggerTable.get(className);
        if (classLogger == null){
            classLogger = new LogUtil(className);
            sLoggerTable.put(className , classLogger);
        }
        return classLogger;
    }

    public static LogUtil fussenLog(){
        if (fussenLog == null){
            fussenLog = new LogUtil(FUSSEN);
        }
        return fussenLog;
    }

    public static LogUtil gaorLog(){
        if (gaorLog == null){
            gaorLog = new LogUtil(GAOR);
        }
        return gaorLog;
    }

    public static LogUtil chunyLog(){
        if (chunLog == null){
            chunLog = new LogUtil(CHUNY);
        }
        return chunLog;
    }

    private String getFunctionName (){
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null){
            return null;
        }
        for (StackTraceElement st : sts){
            if (st.isNativeMethod()){
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())){
                continue;
            }
            if (st.getClassName().equals(this.getClass().getName())){
                continue;
            }
            return mClassName + "[" + Thread.currentThread().getName()
                    + st.getFileName() + ":" + st.getLineNumber() + " "
                    +st.getMethodName()+ "]" ;
        }
        return null ;
    }

    public void i (Objects str){
        if (logFlag){
            if (logLevel <= Log.INFO){
                String name = getFunctionName();
                if (name != null){
                    Log.i(tag , name + " - " + str);
                }else {
                    Log.i(tag , str.toString());
                }
            }
        }
    }

    public void d(Object str) {
        if (logFlag) {
            if (logLevel <= Log.DEBUG) {
                String name = getFunctionName();
                if (name != null) {
                    Log.d(tag, name + " - " + str);
                } else {
                    Log.d(tag, str.toString());
                }
            }
        }
    }


    public void v(Object str) {
        if (logFlag) {
            if (logLevel <= Log.VERBOSE) {
                String name = getFunctionName();
                if (name != null) {
                    Log.v(tag, name + " - " + str);
                } else {
                    Log.v(tag, str.toString());
                }
            }
        }
    }

    public void w(Object str) {
        if (logFlag) {
            if (logLevel <= Log.WARN) {
                String name = getFunctionName();
                if (name != null) {
                    Log.w(tag, name + " - " + str);
                } else {
                    Log.w(tag, str.toString());
                }
            }
        }
    }

    public void e(Object str) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                String name = getFunctionName();
                if (name != null) {
                    Log.e(tag, name + " - " + str);
                } else {
                    Log.e(tag, str.toString());
                }
            }
        }
    }


    public void e(Exception ex) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                Log.e(tag, "error", ex);
            }
        }
    }



    public void e(String log, Throwable tr) {
        if (logFlag) {
            String line = getFunctionName();
            Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}"
                    + "[" + mClassName + line + ":] " + log + "\n", tr);
        }
    }

}
