package com.hm.iou.loginmodule.business.type;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 选择登录方式
 *
 * @author syl
 * @time 2018/5/17 下午4:35
 */
public interface SelectLoginTypeContract {

    interface View extends BaseContract.BaseView {

        /**
         * 隐藏微信登录的方式
         */
        void hideButtonForLoginByWx();
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 手机是否安装微信
         */
        void isInstalledWxChatAPP();

        /**
         * 获取当前手机的微信code
         */
        void getWxCode();

    }
}