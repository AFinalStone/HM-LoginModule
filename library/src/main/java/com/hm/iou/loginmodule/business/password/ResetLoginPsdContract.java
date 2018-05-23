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
        void resetQueryPsdBySMS(String mobile, String checkCode, String newPsd);

        /**
         * 通过活体校验重置登录密码
         *
         * @param mobile
         * @param idCardNum
         * @param newPsd
         * @param livenessIdnumberVerificationSn
         */
        void resetQueryPswdWithLiveness(String mobile, String idCardNum, String newPsd, String livenessIdnumberVerificationSn);

        /**
         * 通过邮箱重置登录密码
         *
         * @param mobile
         * @param mailAddress
         * @param checkCode
         * @param newPsd
         */
        void resetQueryPswdByMail(String mobile, String mailAddress, String checkCode, String newPsd);

        /**
         * 手机登录
         *
         * @param mobile
         * @param loginPsd
         */
        void mobileLogin(String mobile, String loginPsd);

    }
}
