package com.hm.iou.loginmodule.business.loading;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.event.LoginSuccEvent;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginLoadingPresenter extends BaseLoginModulePresenter<LoginLoadingContract.View> implements LoginLoadingContract.Presenter {


    public LoginLoadingPresenter(@NonNull Context context, @NonNull LoginLoadingContract.View view) {
        super(context, view);
    }

    @Override
    public void tokenLogin() {
        UserInfo mUserDataBean = UserManager.getInstance(mContext).getUserInfo();
        String userId = mUserDataBean.getUserId();
        String token = mUserDataBean.getToken();
        LoginModuleApi.tokenLogin(userId, token)
                .compose(getProvider().<BaseResponse<UserInfo>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<UserInfo>handleResponse())
                .subscribeWith(new CommSubscriber<UserInfo>(mView) {
                    @Override
                    public void handleResult(UserInfo userInfo) {
                        UserManager.getInstance(mContext).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                        getHomeData();
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        UserManager.getInstance(mContext).logout();
                        NavigationHelper.toSelectLoginType(mContext);
                        mView.closeCurrPage();
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }
                });
    }

    @Override
    public void getHomeData() {
        Flowable.just(1)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommSubscriber<Integer>(mView) {
                    @Override
                    public void handleResult(Integer integer) {
                        NavigationHelper.toMain(mContext);
                        //发送登录成功的消息，登录模块的所有页面收到这个消息会自动关闭页面
                        EventBus.getDefault().post(new LoginSuccEvent());
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        NavigationHelper.toMain(mContext);
                        //发送登录成功的消息，登录模块的所有页面收到这个消息会自动关闭页面
                        EventBus.getDefault().post(new LoginSuccEvent());
                    }
                });
    }
}