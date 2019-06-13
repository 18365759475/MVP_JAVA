package com.example.myapplication.mvp;

import android.content.Context;

import com.example.myapplication.App.BaseApplication;
import com.example.myapplication.R;
import com.example.myapplication.util.NetWorkUtils;
import com.example.myapplication.view.LoadingDialog;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private Context mContext;
    private String dialogMsg;
    private LoadingDialog loadingDialog;

    private boolean showDialog = true;

    protected CommonSubscriber(Context context) {
        this(context, BaseApplication.getContexe().getString(R.string.loading), false);
    }

    public CommonSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getContexe().getString(R.string.loading), showDialog);
    }

    public CommonSubscriber(Context context, String dialogMsg, boolean showDialog) {
        this.mContext = context;
        this.dialogMsg = dialogMsg;
        this.showDialog = showDialog;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                if (loadingDialog == null)

                loadingDialog.setCancelable(true);
                loadingDialog.setMsg(dialogMsg);
                loadingDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(T t) {
        try {

            if (t instanceof BaseResponse) {
                if (((BaseResponse) t).isTokenWxpire()) {
                    BaseApplication.getInstance().tokenExpire();
                }
            }
            _onNext(t);
        } catch (Exception e) {
            onError(e);
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Throwable e) {
        if (showDialog && loadingDialog != null)
            loadingDialog.cancel();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getInstance())) {
            _onError(BaseApplication.getContexe().getString(R.string.no_net));
        } else if (e instanceof ApiException) {  //服务器
            _onError(e.toString());
        } else if (e instanceof SocketTimeoutException) {//其它
            _onError(BaseApplication.getContexe().getString(R.string.net_error));
        } else {
            _onError("");
        }
        e.printStackTrace();
    }


    @Override
    public void onComplete() {
        if (showDialog && loadingDialog != null)
            loadingDialog.cancel();
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
