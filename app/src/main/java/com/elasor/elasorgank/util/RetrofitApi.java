package com.elasor.elasorgank.util;

import com.elasor.elasorgank.bean.CommonBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @author Elasor
 */
public interface RetrofitApi {

    /**
     * 获取分类数据
     */
    @Headers("cache:"+60)
    @GET("data/{category}/{count}/{page}")
    Observable<CommonBean> requestData(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
