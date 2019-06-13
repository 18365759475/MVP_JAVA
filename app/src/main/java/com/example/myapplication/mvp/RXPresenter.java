package com.example.myapplication.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 基于RXjava的presenter的封装，掌控订阅的生命周期
 */
public abstract class RXPresenter<T extends BaseView> implements BasePresent<T> {
    public Context mContext;
    public T mView;
    public CompositeDisposable compositeDisposable;

    protected void addSubscribe(Disposable subscription) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(subscription);
    }

    protected void UnSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
    protected <U> void addRxBusSubscribe(Class<U> eventType, Consumer<U> act) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(RxBus.getDefault().toDefaultFlowable(eventType, act));
    }

    @Override
    public void UpView() {
        UnSubscribe();
    }

    @Override
    public void UpView(T view, Context context) {
        this.mView = view;
        this.mContext = context;
    }
}
