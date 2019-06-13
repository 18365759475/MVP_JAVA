package com.example.myapplication.mvp;

/**
 * 服务器返回数据异常
 */

public class ApiException extends Exception {
    public ApiException(String msg) {
        super(msg);
    }
}