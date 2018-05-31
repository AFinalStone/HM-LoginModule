package com.hm.iou.loginmodule.business.lancher;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 启动页
 *
 * @author syl
 * @time 2018/5/31 下午2:59
 */
public interface LaunchContract {

    interface View extends BaseContract.BaseView {

        /**
         * 显示广告
         *
         * @param imageUrl
         */
        void showAdvertisement(String imageUrl);

    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 初始化，获取广告
         */
        void init();

        /**
         * 开始倒计时
         */
        void startCountDown();

    }
}
