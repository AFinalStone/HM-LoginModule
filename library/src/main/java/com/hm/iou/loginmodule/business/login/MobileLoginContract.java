package com.hm.iou.loginmodule.business.login;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by syl on 2016/8/2.
 */
public interface MobileLoginContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 通过手机号和密码进行登录
         *
         * @param mobile
         * @param loginPsd
         */
        void mobileLogin(String mobile, String loginPsd);

    }
}
