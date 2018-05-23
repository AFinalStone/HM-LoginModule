package com.hm.iou.loginmodule.business.email;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 通过邮箱找回密码
 *
 * @author syl
 * @time 2018/5/17 下午1:54
 */
public interface BindEmailContract {

    /**
     * 通过邮箱找回密码
     **/
    interface View extends BaseContract.BaseView {
        /**
         * 开始倒计时
         */
        void startCountDown();
    }

    interface Present extends BaseContract.BasePresenter {

        /**
         * 发送重置登录密码的验证码到对应的邮箱上面
         *
         * @param email
         */
        void sendEmailCheckCode(String email);

        /**
         * 用户进行邮箱绑定
         *
         * @param email
         * @param checkCode
         */
        void bindEmail(String email, String checkCode);
    }

}
