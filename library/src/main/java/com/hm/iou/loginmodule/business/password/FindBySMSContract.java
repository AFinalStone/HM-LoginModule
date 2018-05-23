package com.hm.iou.loginmodule.business.password;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 通过手机号短信验证码找回密码
 *
 * @author syl
 * @time 2018/5/17 下午1:53
 */
public interface FindBySMSContract {


    interface View extends BaseContract.BaseView {

    }

    interface Present extends BaseContract.BasePresenter {

        /**
         * 发送重置登录密码的验证码到对应的手机号上面
         *
         * @param mobile
         */
        void sendResetPsdBySMSCheckCode(String mobile);
    }

}
