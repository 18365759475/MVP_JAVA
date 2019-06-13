package com.example.myapplication.api;

import android.os.Build;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.util.DeviceUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.Utf8;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class api {
    //编码格式
    private static final Charset UTF8 = Charset.forName("UTF-8");
    //读超时长，单位：毫秒
    private static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    private static final int CONNECT_TIME_OUT = 7676;

    private static Retrofit initRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                //判断是否为表单数据
                if (request.body() instanceof FormBody) {
                    FormBody.Builder builder1 = new FormBody.Builder();
                    FormBody formBody = (FormBody) request.body();
                    //RequestBody类主要做了获得请求体的数据类型、获得请求体的数据长度、将请求体写入到流中这三件事。
                    RequestBody requestBody = request.body();
                    //流
                    Buffer buffer = new Buffer();
                    //文件写入流中
                    requestBody.writeTo(buffer);
                    //编码格式
                    Charset charset = UTF8;
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }
                    //  公钥加密
//                    try {
//                        builder1.add("req", JwtHelper.createJWT(oldFormBody));//新的参数
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (InvalidKeySpecException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchProviderException e) {
//                        e.printStackTrace();
//                    }
//                    //
//                    builder.method(request.method(), builder1.build());
//                }
//                Request newRequest = builder.build();
//                newRequest.newBuilder()
//                        .header("User-Agent", DeviceUtil.getPhoneBrand() + "-" + DeviceUtil.getPhoneModel() + "-" +
//                                DeviceUtil.getBuildVersion() + " -appversionName:" + DeviceUtil.getVersionName(MyApplication.getInstance()))
//                        .build();
                }
                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor);
        if (BuildConfig.LOG_DEBUG) {
            builder.addInterceptor(loggingInterceptor);
        }

        return new Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ConstantsKey.BaseUrl).build();
    }

    public static apiService apiService;

    public synchronized static apiService createService() {
        if (apiService == null)
            return apiService = initRetrofit().create(apiService.class);
        else
            return apiService;
    }


}
