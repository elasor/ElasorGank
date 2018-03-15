package com.lify.elasor.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Elasor
 */
@SuppressWarnings("unused")
public class ElasorRetrofit {

    private ElasorRetrofit(){

    }

    public static <T> T get(String url, Class<T> cls){
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(ElasorHttp.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(cls);
    }
}
