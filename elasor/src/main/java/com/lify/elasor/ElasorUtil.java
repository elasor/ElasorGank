package com.lify.elasor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author Elasor
 */
@SuppressWarnings("unused")
public class ElasorUtil {

    /**
     * dp转换成px
     * @param context 上下文
     * @param dp dp
     * @return px
     */
    public static int dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转换成dp
     * @param context 上下文
     * @param px px
     * @return dp
     */
    public static int px2dp(Context context, float px){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "0.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "0.0.0";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /*
     * 获取IMEI
     * 需要READ_PHONE_STATE权限
	 */
    @SuppressLint("HardwareIds")
    public static String getDeviceIMEI(Context context) {
        String deviceIMEI = "";

        try {
            TelephonyManager tm = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceIMEI;
    }

    /**
     * 获取序列号
     * @return 序列号
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
            System.out.println(serial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }
}
