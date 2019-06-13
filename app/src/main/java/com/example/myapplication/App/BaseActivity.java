package com.example.myapplication.App;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.myapplication.R;
import com.example.myapplication.eyesutils.Eyes;
import com.example.myapplication.mvp.BasePresent;
import com.example.myapplication.mvp.BaseView;
import com.example.myapplication.util.AndroidBug5497Workaround;
import com.example.myapplication.util.DisplayUtil;
import com.example.myapplication.util.NetWorkUtils;
import com.example.myapplication.util.StatusBarUtils;
import com.example.myapplication.util.TUtil;
import com.example.myapplication.util.ToastUtil;

import butterknife.ButterKnife;

/**
 * 基类
 * Created by Circle on 2017/4/2 0002.
 */

public abstract class BaseActivity<T extends BasePresent> extends FragmentActivity implements BaseView {

    public T mPresenter;

    public Context mContext;
    private final static String TAG = "UmengActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 把actvity放到application栈中管理
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.toolbar_backgroud));
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.UpView(this, this);
        }
/**
 * 设置组件化的Log开关
 * 参数: boolean 默认为false，如需查看LOG设置为true
 */
//        UMConfigure.setLogEnabled(true);
//        //友盟错误统计
//        MobclickAgent.onPageStart(TAG);//页面启动事件。
//        MobclickAgent.onProfileSignIn(TAG);//登录事件。
//        MobclickAgent.setCatchUncaughtExceptions(true);
//        /**
//         * 设置日志加密
//         * 参数：boolean 默认为false（不加密）
//         */
//        UMConfigure.setEncryptEnabled(true);
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        //设置session时间间隔    一秒
//        MobclickAgent.setSessionContinueMillis(1000);
        AppManager.getAppManager().addActivity(this);
        initEventAndData();
    }
    public void setTitleColor(int color){
        StatusBarUtils.with(this).setDrawable(getResources().getDrawable(R.drawable.toolbar_background)).init();
    }
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AndroidBug5497Workaround.assistActivity(this);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
//            window.setNavigationBarColor(color);
        }

    }


    public void setTransparentStatusBar() {
        setStatusBarColor(Color.TRANSPARENT);
    }

    public void setStatusBarPadding(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setPadding(0, DisplayUtil.getStatusBarHeight(getApplicationContext()), 0, 0);
        }
    }


    /**********子类实现**********/
    //获取布局文件
    public abstract int getLayoutId();

    //初始化view
    public abstract void initEventAndData();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 网络访问错误提醒
     */
    public void httpError() {
        if (NetWorkUtils.isNetConnected(getApplicationContext()))
            ToastUtil.show(R.string.net_error);
        else
            ToastUtil.show(R.string.no_net);
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.UpView();
        AppManager.getAppManager().finishActivity(this);
    }
    @Override
    public void onResume() {
        super.onResume();
//        onProfileSignOff // 注销事件。
//        onKillProcess // 杀死进程事件。

    }
    @Override
    public void onPause() {
        super.onPause();


    }
}
