package com.example.myapplication.mvp;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public int code;
    public T data;
    public String msg;

    public boolean success() {
        return code == 200;
    }

    public boolean isTokenWxpire(){
        return code ==406;
    }
}
