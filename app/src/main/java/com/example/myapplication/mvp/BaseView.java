package com.example.myapplication.mvp;

/**
 * view 基类
 */
public interface BaseView {

    void showError(int Code, String msg);

    void useNightMode(boolean isNight);
}
