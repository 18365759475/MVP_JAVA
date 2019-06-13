package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.App.BaseActivity;
import com.example.myapplication.Bean.NewsInfoBean;
import com.example.myapplication.Presenter.MainActivity.MainContract;
import com.example.myapplication.Presenter.MainActivity.MainPresenter;
import com.example.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainA2ctivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.why)
    TextView why;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void hhhhh(NewsInfoBean newsInfoBean) {

    }

    @Override
    public void showError(int Code, String msg) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.why)
    public void onViewClicked() {
        mPresenter.Ggg("NewsTypeKey", 1);
    }
}
