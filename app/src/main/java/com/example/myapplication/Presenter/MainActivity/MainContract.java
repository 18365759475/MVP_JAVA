package com.example.myapplication.Presenter.MainActivity;

import com.example.myapplication.Bean.NewsInfoBean;
import com.example.myapplication.mvp.BasePresent;
import com.example.myapplication.mvp.BaseView;

public interface MainContract {
    interface View extends BaseView {
        void hhhhh(NewsInfoBean newsInfoBean);
    }

    interface Presenter extends BasePresent<View> {
        void Ggg(String newsId, int page);
    }
}
