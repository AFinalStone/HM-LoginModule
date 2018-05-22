package com.hm.iou.loginmodule.business.loading;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by syl on 2017年12月4日 17:23:10
 */
public interface LoginLoadingContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 令牌登录
         */
        void tokenLogin();

        /**
         * 获取首页数据，进行预加载，至少延时1.5秒
         */
        void getHomeData();
    }
}