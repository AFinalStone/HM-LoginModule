package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.login.MobileLoginContract;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.event.CommBizEvent;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindBySMSPresenter extends BaseLoginModulePresenter<FindBySMSContract.View> implements FindBySMSContract.Present {

    //重置登录密码
    private final int PURPOSE_TYPE_RESET_LOGIN_PSD_BY_SMS = 2;

    public FindBySMSPresenter(@NonNull Context context, @NonNull FindBySMSContract.View view) {
        super(context, view);
    }


    private void timeCountDown() {
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 59 - aLong;
                    }
                })
                .take(60)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeWith(new CommSubscriber<Long>(mView) {

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (mView != null) {
                            String getCode = "重发验证码";
                            mView.setGetSMSBtnText(true, getCode);
                        }
                    }

                    @Override
                    public void handleResult(Long time) {
                        if (mView != null) {
                            String countDownDesc = "%S秒后重试";
                            countDownDesc = String.format(countDownDesc, time);
                            mView.setGetSMSBtnText(false, countDownDesc);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }
                });


    }

    @Override
    public void sendSMSCheckCode(String mobile) {
        mView.showLoadingView();
        LoginModuleApi.sendMessage(PURPOSE_TYPE_RESET_LOGIN_PSD_BY_SMS, mobile)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String str) {
                        mView.dismissLoadingView();
                        mView.toastMessage(R.string.uikit_get_check_code_success);
                        timeCountDown();
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

    @Override
    public void compareSMSCheckCode(final String mobile, final String checkCode) {
        mView.showLoadingView();
        LoginModuleApi.compareSMSCheckCode(mobile, checkCode)
                .compose(getProvider().<BaseResponse<Integer>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Integer>handleResponse())
                .subscribeWith(new CommSubscriber<Integer>(mView) {
                    @Override
                    public void handleResult(Integer integer) {
                        mView.dismissLoadingView();
                        NavigationHelper.toResetLoginPsdBySMS(mContext, mobile, checkCode);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                        mView.warnCheckFailed();
                    }
                });
    }
}
