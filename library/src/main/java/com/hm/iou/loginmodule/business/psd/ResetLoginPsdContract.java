package com.hm.iou.loginmodule.business.psd;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by AFinalStone on 2016/8/2.
 */
public interface ResetLoginPsdContract {

    interface View extends BaseContract.BaseView {

        void resetQueryPsdSuccess();

        void loginSuccess();

    }

    interface Presenter extends BaseContract.BasePresenter {

        //通过短信验证码重置登录密码
        void resetQueryPsdBySMS(String mobile, String checkCode, String newPswd);

        //通过活体校验重置登录密码
        void resetQueryPswdWithLiveness(String loginName, String idCardNum, String transPswd, String livenessIdnumberVerificationSn);

        //通过邮箱重置登录密码
        void resetQueryPswdByMail(String mobile, String mailAddr, String checkCode, String newPswd);

        //手机登录
        void mobileLogin(String userPhone, String userPassword);

    }
}
