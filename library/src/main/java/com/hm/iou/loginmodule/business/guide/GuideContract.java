package com.hm.iou.loginmodule.business.guide;


import com.hm.iou.base.mvp.BaseContract;

import java.util.List;

/**
 * 启动页
 *
 * @author syl
 * @time 2018/5/31 下午2:59
 */
public interface GuideContract {

    interface View extends BaseContract.BaseView {
        /**
         * 显示viewPager
         */
        void showViewPager(List<Integer> list);

        /**
         * 隐藏微信登录的方式
         */
        void hideButtonForLoginByWx();

        //只显示微信登录
        void showWXLoginOnly();

        //隐藏只有微信登录
        void hideWXLoginOnly();
    }

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 初始化
         */
        void init();

        /**
         * 获取当前手机的微信code
         */
        void getWxCode();

        void checkVersion();
    }
}
