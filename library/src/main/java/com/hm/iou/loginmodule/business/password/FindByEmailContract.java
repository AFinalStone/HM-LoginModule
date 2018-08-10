package com.hm.iou.loginmodule.business.password;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 通过邮箱找回密码
 *
 * @author syl
 * @time 2018/5/17 下午1:54
 */
public interface FindByEmailContract {

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
        void sendEmailCheckCode(String mobile, String email);

        /**
         * 短信重置密码前,短信验证码的比对
         *
         * @param email
         * @param checkCode
         */
        void compareEmailCheckCode(String mobile, String email, String checkCode);
    }

}
