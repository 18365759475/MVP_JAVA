package com.example.myapplication.mvp;

import android.text.TextUtils;


import com.example.myapplication.App.BaseApplication;
import com.example.myapplication.util.ToastUtil;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx管理线程，以及访问网络
 */
public class RxUtil {
    /**
     * 统一线程处理
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseResponse<T>, T> handleResult() {
        return new FlowableTransformer<BaseResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        if (tBaseResponse.isTokenWxpire()) {
                            BaseApplication.getInstance().tokenExpire();
                            return Flowable.error(new ApiException(tBaseResponse.msg));
                        } else if (tBaseResponse.success()) {
                            if (tBaseResponse.data != null) {
                                return createData(tBaseResponse.data);
                            } else {
                                ToastUtil.show(tBaseResponse.msg);
                                return null;
                            }
                        } else {
                            if (tBaseResponse == null || TextUtils.isEmpty(tBaseResponse.msg)) {
                                return Flowable.error(new ApiException("服务器返回error"));
                            } else {
                                return Flowable.error(new ApiException(tBaseResponse.msg));
                            }
                        }

                    }
                });
            }
        };
    }


    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }

            }
        }, BackpressureStrategy.BUFFER);
    }
}
