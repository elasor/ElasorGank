package com.lify.elasor.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lify.elasor.msg.ElasorLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Elasor
 */
@SuppressWarnings("unused")
public class ElasorHttp {

    private static volatile ElasorHttp elasorHttp;
    private static OkHttpClient okHttpClient;
    private static Context mContext;

    private ElasorHttp(Context context){
        mContext = context;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String s) {
//                ElasorLog.e("HttpLoggingInterceptor --> "+s);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .cache(cache)
                .build();
    }

    public static void init(Context context){
        if(null == elasorHttp){
            synchronized (ElasorHttp.class){
                if(null == elasorHttp){
                    elasorHttp = new ElasorHttp(context.getApplicationContext());
                }
            }
        }
    }

    /**
     * 获得OkHttpClient
     * @return OkHttpClient实例
     */
    public static OkHttpClient getClient(){
        if(null == okHttpClient){
            throw new NullPointerException("okHttpClient has not been initialized. Maybe you should call ElasorHttp.init() first at your application!");
        }
        return okHttpClient;
    }

    /**
     * get
     * @param url 访问地址
     * @param callback 异步回调
     */
    public static void get(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post
     * @param url 访问地址
     * @param formBody 提交参数
     * @param callback 异步回调
     */
    public static void post(String url, FormBody formBody, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 判断网络是否连接
     * @param context 上下文对象
     * @return 网络是否连接  true-已连接 false-未连接
     */
    public static boolean isConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    private class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!ElasorHttp.isConnected(mContext)){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                ElasorLog.e("there is no network here!");
            }
            Response proceed = chain.proceed(request);
            if(ElasorHttp.isConnected(mContext)){
                String cacheControl = request.cacheControl().toString();
                return proceed.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return proceed.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    private static final int TIMEOUT_CONNECT = 5; //5秒
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            //获取retrofit @headers里面的参数，参数可以自己定义，在本例我自己定义的是cache，跟@headers里面对应就可以了
            String cache = chain.request().header("cache");
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            //如果cacheControl为空，就让他TIMEOUT_CONNECT秒的缓存，本例是5秒，方便观察。注意这里的cacheControl是服务器返回的
            if (cacheControl == null) {
                //如果cache没值，缓存时间为TIMEOUT_CONNECT，有的话就为cache的值
                if (cache == null || "".equals(cache)) {
                    cache = TIMEOUT_CONNECT + "";
                }
                originalResponse = originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + cache)
                        .build();
                return originalResponse;
            } else {
                return originalResponse;
            }
        }
    };

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //离线的时候为7天的缓存。
            if (!ElasorHttp.isConnected(mContext)) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale="+TIMEOUT_DISCONNECT)
                        .build();
            }
            return chain.proceed(request);
        }
    };
}
