package com.lify.elasor.dialog.loading;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lify.elasor.R;

/**
 * @author Elasor
 */
@SuppressWarnings("all")
public class ElasorLoadingDialog {

    private Context mContext;
    private AlertDialog mDialog;
    private View mView;

    private String mMessage;
    private boolean mCancelable = false;
    private boolean mCanceledOnTouchOutside = false;
    private OnDialogDismissListener mOnDialogDismissListener;
    private int mGravity;

    public ElasorLoadingDialog(Context context) {
        this(context, R.style.elasor_loading);
    }

    public ElasorLoadingDialog(Context context, int dialogStyle) {
        this.mContext = context;

        mView = View.inflate(context, R.layout.dialog_elasor_loading, null);

        mDialog = new AlertDialog.Builder(context, dialogStyle)
                .setView(mView)
                .create();
    }

    public ElasorLoadingDialog setMessage(@StringRes int messageRes){
        setMessage(mContext.getResources().getString(messageRes));
        return this;
    }

    public ElasorLoadingDialog setMessage(String message){
        this.mMessage = message;
        return this;
    }

    public ElasorLoadingDialog setGravity(int gravity){
        this.mGravity = gravity;
        return this;
    }

    public ElasorLoadingDialog setCancelable(boolean cancelable){
        this.mCancelable = cancelable;
        return this;
    }

    public ElasorLoadingDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public ElasorLoadingDialog setOnDismissListener(OnDialogDismissListener onDismissListener){
        this.mOnDialogDismissListener = onDismissListener;
        return this;
    }

    private void initView() {
        TextView mTvDialogMessage = ((TextView) mView.findViewById(R.id.tv_dialog_message));
        ProgressBar mPbDialogProgress = ((ProgressBar) mView.findViewById(R.id.pb_dialog_progress));

        mTvDialogMessage.setText(TextUtils.isEmpty(mMessage)?"正在处理中..":mMessage);
    }

    private void initDialog() {
        Window window = mDialog.getWindow();
        window.setGravity(mGravity==0?Gravity.BOTTOM:mGravity);
        window.setWindowAnimations(R.style.elasor_loading);

        mDialog.setCancelable(mCancelable);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mContext = null;
                mDialog = null;
                mView = null;
                if(null != mOnDialogDismissListener){
                    mOnDialogDismissListener.onDialogDismiss();
                }
            }
        });
    }

    public ElasorLoadingDialog show(){
        initView();

        initDialog();

        mDialog.show();
        return this;
    }

    public void dismiss(){
        if(null != mDialog && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    public interface OnDialogDismissListener{
        void onDialogDismiss();
    }
}
