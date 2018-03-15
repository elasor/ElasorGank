package com.elasor.elasorgank;

import android.app.Application;

import com.lify.elasor.http.ElasorHttp;

/**
 * @author Elasor
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ElasorHttp.init(this);
    }
}
