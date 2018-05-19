package com.hm.iou.loginmodule.business.register.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;

/**
 * 通过手机号进行注册
 *
 * @author syl
 * @time 2018/5/19 下午5:21
 */
public class RegisterByMobilePresenter extends MvpActivityPresenter<RegisterByMobileContract.View> implements RegisterByMobileContract.Presenter {


    public RegisterByMobilePresenter(@NonNull Context context, @NonNull RegisterByMobileContract.View view) {
        super(context, view);
    }

    @Override
    public void getCode(String phoneNumber) {

    }

    @Override
    public void registerAndLogin(String loginName, String queryPswd, String checkCode) {

    }
}
