package com.lify.elasor.msg;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Elasor
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ElasorToast {

    private static Toast toast;

    public static void make(Context context, int msg){
        make(context, msg, Toast.LENGTH_SHORT);
    }

    public static void make(Context context, String msg){
        make(context, msg, Toast.LENGTH_SHORT);
    }

    public static void make(Context context, int msg, int duration){
        String msgStr = context.getString(msg);
        make(context, msgStr, duration);
    }

    public static void make(Context context, String msg, int duration){
        if(null == toast){
            toast = Toast.makeText(context, msg, duration);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
}
