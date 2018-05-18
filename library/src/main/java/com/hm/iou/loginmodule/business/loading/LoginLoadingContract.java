package com.hm.iou.loginmodule.business.loading;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by AFinalStone on 2017年12月4日 17:23:10
 */
public interface LoginLoadingContract {

    interface View extends BaseContract.BaseView {


        void tokenLoginSuccess();

        void tokenLoginFailed(Throwable e);

        void getHomeDataSuccess();

        void getHomeDataFailed();
    }

    interface Presenter extends BaseContract.BasePresenter {

        void tokenLogin();

        void getHomeData();
    }
}