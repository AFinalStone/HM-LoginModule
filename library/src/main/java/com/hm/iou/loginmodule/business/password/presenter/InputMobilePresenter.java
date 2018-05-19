package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;
import com.hm.iou.loginmodule.business.password.InputMobileContract;

/**
 * 通过手机号进行登录
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class InputMobilePresenter extends MvpActivityPresenter<InputMobileContract.View> implements InputMobileContract.Presenter {

    public InputMobilePresenter(@NonNull Context context, @NonNull InputMobileContract.View view) {
        super(context, view);
    }

    @Override
    public void getResetPwsMethod(String userPhone) {

    }
}
