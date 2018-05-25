package com.hm.iou.loginmodule.business;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.BaseContract.BaseView;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.sharedata.event.LoginSuccEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author : syl
 * @Date : 2018/5/22 11:35
 * @E-Mail : shiyaolei@dafy.com
 */
public abstract class BaseLoginModulePresenter<T extends BaseView> extends MvpActivityPresenter<T> {

    public BaseLoginModulePresenter(@NonNull Context context, @NonNull T view) {
        super(context, view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 用户成功登陆，关闭当前页面
     *
     * @param loginSucceedEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenLoginSuccess(LoginSuccEvent loginSucceedEvent) {
        mView.closeCurrPage();
    }
}
