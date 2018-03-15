package com.lify.elasor.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lify.elasor.msg.ElasorToast;

/**
 * @author Elasor
 */
@SuppressWarnings("all")
public abstract class ElasorFragment extends Fragment{

    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null == mView){
            mView = inflater.inflate(layoutResId(), container, false);
            initControls();
            initControlListener();
            initOther();
        }
        return mView;
    }

    protected void initFragment() {

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

    public void showToast(String msg){
        ElasorToast.make(getContext(), msg, Toast.LENGTH_SHORT);
    }

    public void showToast(@StringRes int msgRes){
        ElasorToast.make(getContext(), msgRes, Toast.LENGTH_SHORT);
    }
}
