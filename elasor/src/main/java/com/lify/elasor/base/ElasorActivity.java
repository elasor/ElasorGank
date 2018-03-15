package com.lify.elasor.base;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lify.elasor.msg.ElasorToast;

/**
 * @author Elasor
 */
@SuppressWarnings({"all"})
public abstract class ElasorActivity extends AppCompatActivity{

    protected SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        setContentView(layoutResId());
        initControls();
        initControlListener();
        initOther();
    }

    /**
     * 初始化Activity
     */
    protected void initActivity(){
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * 布局文件
     * @return 布局文件资源ID
     */
    protected abstract int layoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initControls();

    /**
     * 初始化控件监听事件
     */
    protected abstract void initControlListener();

    /**
     * 其它
     */
    protected void initOther() {

    }

    /**
     * 隐藏软键盘，always
     */
    protected void hideSoftKeyboard() {
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        getWindow().addFlags(flags);
    }

    /**
     * 透明状态栏
     */
    protected void immersive(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    


    public void showToast(String msg){
        ElasorToast.make(this, msg, Toast.LENGTH_SHORT);
    }

    public void showToast(@StringRes int msgRes){
        ElasorToast.make(this, msgRes, Toast.LENGTH_SHORT);
    }
}
