package com.hm.iou.loginmodule.business.lancher;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;

/**
 * @author syl
 * @time 2018/5/31 下午3:01
 */
public class LaunchPresenter extends MvpActivityPresenter<LaunchContract.View> implements LaunchContract.Presenter {


    public LaunchPresenter(@NonNull Context context, @NonNull LaunchContract.View view) {
        super(context, view);
    }

    @Override
    public void init() {

    }

    @Override
    public void startCountDown() {

    }
}
