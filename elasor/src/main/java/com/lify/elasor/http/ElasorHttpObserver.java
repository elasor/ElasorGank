package com.lify.elasor.http;

import android.content.Context;
import android.support.annotation.StringRes;

import com.google.gson.JsonParseException;
import com.lify.elasor.R;
import com.lify.elasor.dialog.loading.ElasorLoadingDialog;
import com.lify.elasor.msg.ElasorToast;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author Elasor
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ElasorHttpObserver<T> implements Observer<T>{

    private Context mContext;
    private ElasorLoadingDialog loadingDialog;

    public ElasorHttpObserver(Context context) {
        this(context, true);
    }

    public ElasorHttpObserver(Context context, boolean isLoading) {
        this.mContext = context;

        if(isLoading){
            loadingDialog = new ElasorLoadingDialog(context).show();
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        if(null != loadingDialog){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(null != loadingDialog){
            loadingDialog.dismiss();
            loadingDialog = null;
        }

        if (e instanceof HttpException) {
            showToast(R.string.httpException);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            showToast(R.string.connectException);
        } else if (e instanceof InterruptedException) {
            showToast(R.string.interruptedException);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            showToast(R.string.jsonException);
        } else {
            showToast(R.string.unknowException);
        }
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }

    private void showToast(String msg){
        ElasorToast.make(mContext, msg);
    }

    private void showToast(@StringRes int msg){
        ElasorToast.make(mContext, msg);
    }
}
