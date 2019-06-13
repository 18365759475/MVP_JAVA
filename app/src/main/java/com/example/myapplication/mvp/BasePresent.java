package com.example.myapplication.mvp;

import android.content.Context;

/**
 * Presenter 基类
 */
public interface BasePresent<T extends BaseView> {
    void UpView(T view, Context context);

    void UpView();
}
