package com.example.myapplication.api;

import com.example.myapplication.Bean.NewsInfoBean;
import com.example.myapplication.mvp.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface apiService {

    String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 填入接口
    /**
     * 要闻
     *
     * @param type
     * @param id
     * @param startPage
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Flowable<BaseResponse<NewsInfoBean>> getImportantNews(@Path("type") String type,
                                                            @Path("id") String id,
                                                            @Path("startPage") int startPage);
}
