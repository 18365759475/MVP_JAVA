package com.example.myapplication.Presenter.MainActivity;

import com.example.myapplication.Bean.NewsInfoBean;
import com.example.myapplication.api.api;
import com.example.myapplication.mvp.BaseResponse;
import com.example.myapplication.mvp.CommonSubscriber;
import com.example.myapplication.mvp.RXPresenter;
import com.example.myapplication.mvp.RxUtil;

public class MainPresenter extends RXPresenter<MainContract.View>implements MainContract.Presenter {
    private static final String HEAD_LINE_NEWS = "T1348647909107";
    public static final int INCREASE_PAGE = 20;
    @Override
    public void Ggg(String newsId, int page) {
        String type;
        if (newsId != null && newsId.equals(HEAD_LINE_NEWS))
            type = "headline";
        else
            type = "list";
        addSubscribe(api.createService().getImportantNews(type,newsId,page * INCREASE_PAGE)
                .compose(RxUtil.<BaseResponse<NewsInfoBean>>rxSchedulerHelper())
                .compose(RxUtil.<NewsInfoBean>handleResult())
                .subscribeWith(new CommonSubscriber<NewsInfoBean>(mContext,true) {
                    @Override
                    protected void _onNext(NewsInfoBean introduceCommBean) {
                        if (introduceCommBean!=null){
                            mView.hhhhh(introduceCommBean);
                        }
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                }));
    }
}
