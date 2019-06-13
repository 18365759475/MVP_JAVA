package com.example.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * description:弹窗浮动加载进度条
 * Created by Circle on 2017/4/2 0002.
 */

public class LoadingDialog extends Dialog {
    TextView tvMsg;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null, true);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

    }

    public LoadingDialog setMsg(String message) {
        tvMsg.setText(message);
        return this;
    }

    /**
     * 关闭加载对话框
     */
    public void cancelDialog() {
            cancel();
    }
}
