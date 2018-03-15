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
public abstract class ElasorLazyFragment extends Fragment {

    protected View mView;
    protected boolean mIsVisible = false;
    protected boolean mHasCreated = false; //标识onCreateView是否已调用

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(null == mView) {
            mView = inflater.inflate(layoutResId(), container, false);
            initControls();
            initControlListener();
            initOther();
            onVisible();
        }
        mHasCreated = true;
        loadData();
        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            mIsVisible = true;
            onVisible();
        }else{
            mIsVisible = false;
            onInvisible();
        }
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
     * 加载数据
     */
    protected abstract void loadData();

    protected void onVisible(){
        loadData();
    }

    protected void onInvisible(){

    }

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
