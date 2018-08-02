package com.hm.iou.loginmodule.business.password;


import android.support.annotation.StringRes;

import com.hm.iou.base.mvp.BaseContract;

/**
 * 通过手机号短信验证码找回密码
 *
 * @author syl
 * @time 2018/5/17 下午1:53
 */
public interface FindBySMSContract {


    interface View extends BaseContract.BaseView {

        /**
         * 修改获取验证码按钮的状态和内容
         *
         * @param enable
         * @param text   文字资源的id
         */
        void setGetSMSBtnText(boolean enable, String text);

        /**
         * 校验失败
         */
        void warnCheckFailed();
    }

    interface Present extends BaseContract.BasePresenter {

        /**
         * 发送重置登录密码的验证码到对应的手机号上面
         *
         * @param mobile
         */
        void sendSMSCheckCode(String mobile);

        /**
         * 短信重置密码前,短信验证码的比对
         *
         * @param mobile
         * @param checkCode
         */
        void compareSMSCheckCode(String mobile, String checkCode);
    }

}
