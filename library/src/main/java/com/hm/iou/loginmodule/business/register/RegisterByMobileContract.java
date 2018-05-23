package com.hm.iou.loginmodule.business.register;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by syl on 2017年12月4日 17:23:10
 */
public interface RegisterByMobileContract {

    interface View extends BaseContract.BaseView {
        /**
         * 开始倒计时
         */
        void startCountDown();
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 发送短信验证码
         *
         * @param mobile
         */
        void getSMSCode(String mobile);

        /**
         * 通过手机号进行账号注册
         *
         * @param mobile
         * @param loginPsd
         * @param smsCheckCode
         */
        void registerByMobileAndLogin(String mobile, String loginPsd, String smsCheckCode);
    }
}
