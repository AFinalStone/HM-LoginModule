package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByEmailContract;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;

/**
 * 通过手机号进行登录
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindByEmailPresenter extends BaseLoginModulePresenter<FindByEmailContract.View> implements FindByEmailContract.Present {


    public FindByEmailPresenter(@NonNull Context context, @NonNull FindByEmailContract.View view) {
        super(context, view);
    }

    @Override
    public void sendEmailCheckCode(String email) {

    }
}
