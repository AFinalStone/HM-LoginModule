package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.ResetLoginPsdContract;

/**
 * 通过手机号进行登录
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class ResetLoginPsdPresenter extends BaseLoginModulePresenter<ResetLoginPsdContract.View> implements ResetLoginPsdContract.Presenter {


    public ResetLoginPsdPresenter(@NonNull Context context, @NonNull ResetLoginPsdContract.View view) {
        super(context, view);
    }

    @Override
    public void resetQueryPsdBySMS(String mobile, String checkCode, String newPswd) {

    }

    @Override
    public void resetQueryPswdWithLiveness(String loginName, String idCardNum, String transPswd, String livenessIdnumberVerificationSn) {

    }

    @Override
    public void resetQueryPswdByMail(String mobile, String mailAddr, String checkCode, String newPswd) {

    }

    @Override
    public void mobileLogin(String userPhone, String userPassword) {

    }
}
