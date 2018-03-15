package com.lify.elasor.dialog.other;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lify.elasor.R;

/**
 * @author Elasor
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ElasorDialog {

    private Context mContext;
    private View mView;
    private AlertDialog mDialog;

    private String mTitle;
    private String mMessage;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private int mGravity;
    private String mNegativeText;
    private OnClickListener mNegativeListener;
    private String mPositiveText;
    private OnClickListener mPositiveListener;

    public ElasorDialog(Context context) {
        this(context, R.style.elasor_loading);
    }

    public ElasorDialog(Context context,@StyleRes int style) {
        this.mContext = context;

        mView = View.inflate(context, R.layout.dialog_elasor, null);

        mDialog = new AlertDialog.Builder(context, style)
                .setView(mView)
                .create();
    }

    public ElasorDialog setTitle(@StringRes int titleRes){
        this.setTitle(mContext.getResources().getString(titleRes));
        return this;
    }

    public ElasorDialog setTitle(String title){
        this.mTitle = title;
        return this;
    }

    public ElasorDialog setMessage(@StringRes int messageRes){
        setMessage(mContext.getResources().getString(messageRes));
        return this;
    }

    public ElasorDialog setMessage(String message){
        this.mMessage = message;
        return this;
    }

    public ElasorDialog setGravity(int gravity){
        this.mGravity = gravity;
        return this;
    }

    public ElasorDialog setCancelable(boolean cancelable){
        this.mCancelable = cancelable;
        return this;
    }

    public ElasorDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public ElasorDialog setNegativeButton(String text, OnClickListener onClickListener){
        this.mNegativeText = text;
        this.mNegativeListener = onClickListener;
        return this;
    }

    public ElasorDialog setPositiveButton(String text, OnClickListener onClickListener){
        this.mPositiveText = text;
        this.mPositiveListener = onClickListener;
        return this;
    }

    private void initView() {
        TextView mTvDialogTitle = ((TextView) mView.findViewById(R.id.tv_dialog_title));
        TextView mTvDialogMessage = ((TextView) mView.findViewById(R.id.tv_dialog_message));
        TextView mTvDialogNegative = ((TextView) mView.findViewById(R.id.tv_dialog_negative));
        TextView mTvDialogPositive = ((TextView) mView.findViewById(R.id.tv_dialog_positive));
        View llDialogButton = mView.findViewById(R.id.ll_dialog_button);
        View vDialog1 = mView.findViewById(R.id.v_dialog_1);
        View vDialog2 = mView.findViewById(R.id.v_dialog_2);

        mTvDialogTitle.setText(mTitle);
        mTvDialogTitle.setVisibility(TextUtils.isEmpty(mTitle)?View.GONE:View.VISIBLE);

        mTvDialogMessage.setText(mMessage);

        mTvDialogNegative.setText(mNegativeText);
        mTvDialogNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mNegativeListener){
                    mNegativeListener.onClick(mDialog);
                }
            }
        });
        mTvDialogNegative.setVisibility(TextUtils.isEmpty(mNegativeText)?View.GONE:View.VISIBLE);

        mTvDialogPositive.setText(mPositiveText);
        mTvDialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mPositiveListener){
                    mPositiveListener.onClick(mDialog);
                }
            }
        });
        mTvDialogPositive.setVisibility(TextUtils.isEmpty(mPositiveText)?View.GONE:View.VISIBLE);

        boolean is1Show = !TextUtils.isEmpty(mNegativeText) || !TextUtils.isEmpty(mPositiveText);
        boolean is2Show = !TextUtils.isEmpty(mNegativeText) && !TextUtils.isEmpty(mPositiveText);

        vDialog1.setVisibility(is1Show?View.VISIBLE:View.GONE);
        vDialog2.setVisibility(is2Show?View.VISIBLE:View.GONE);
    }

    private void initDialog() {

        Window window = mDialog.getWindow();
        if(null != window){
            window.setGravity(mGravity==0? Gravity.BOTTOM:mGravity);
            window.setWindowAnimations(R.style.elasor_loading);
        }

        mDialog.setCancelable(mCancelable);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mContext = null;
                mDialog = null;
                mView = null;
            }
        });
    }

    public ElasorDialog show(){
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

    public interface OnClickListener{
        void onClick(AlertDialog dialog);
    }
}
