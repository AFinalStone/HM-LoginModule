package com.hm.iou.loginmodule.business.password;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by syl on 2016/8/2.
 */
public interface ResetLoginPsdContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 通过短信验证码重置登录密码
         *
         * @param mobile
         * @param checkCode
         * @param newPsd
         */
        void resetLoginPsdBySMS(String mobile, String checkCode, String newPsd);

        /**
         * 通过活体校验重置登录密码
         *
         * @param mobile
         * @param idCardNum   身份证前六位
         * @param faceCheckSn 活体校验的流水号
         * @param newPsd
         */
        void resetLoginPsdByFace(String mobile, String idCardNum, String faceCheckSn, String newPsd);

        /**
         * 通过邮箱验证码重置登录密码
         *
         * @param email
         * @param checkCode
         * @param sn
         * @param newPsd
         */
        void resetLoginPsdByEMail(String mobile, String email, String checkCode, String sn, String newPsd);

        /**
         * 手机登录
         *
         * @param mobile
         * @param loginPsd
         */
        void mobileLogin(String mobile, String loginPsd);

    }
}
