package com.lify.elasor.msg;

import android.util.Log;

/**
 * @author Elasor
 */
@SuppressWarnings("all")
public class ElasorLog {

    private static String getClassTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(ElasorLog.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    /**
     * 错误
     * @param msg 信息
     */
    public static void e(String msg){
        Log.e(getClassTag(), msg);
    }

    /**
     * 警告
     * @param msg 信息
     */
    public static void w(String msg){
        Log.w(getClassTag(), msg);
    }

    /**
     * 信息
     * @param msg 信息
     */
    public static void i(String msg){
        Log.i(getClassTag(), msg);
    }

    /**
     * debug
     * @param msg 信息
     */
    public static void d(String msg){
        Log.d(getClassTag(), msg);
    }

    /**
     * verbose
     * @param msg 信息
     */
    public static void v(String msg){
        Log.v(getClassTag(), msg);
    }


}
