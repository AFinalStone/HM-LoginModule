package com.hm.iou.loginmodule.business.loading;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;

public class LoginLoadingPresenter extends MvpActivityPresenter<LoginLoadingContract.View> implements LoginLoadingContract.Presenter {


    public LoginLoadingPresenter(@NonNull Context context, @NonNull LoginLoadingContract.View view) {
        super(context, view);
    }

    @Override
    public void tokenLogin() {
    }

    @Override
    public void getHomeData() {

    }
}